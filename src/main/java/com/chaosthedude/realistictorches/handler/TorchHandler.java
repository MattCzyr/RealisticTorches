package com.chaosthedude.realistictorches.handler;

import com.chaosthedude.realistictorches.RealisticTorchesBlocks;
import com.chaosthedude.realistictorches.RealisticTorchesItems;
import com.chaosthedude.realistictorches.config.ConfigHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class TorchHandler {

	public static void extinguishTorch(World world, int x, int y, int z, boolean extinguishFully) {
		if (extinguishFully || world.getBlock(x, y, z) == RealisticTorchesBlocks.torchSmoldering) {
			playTorchSound(world, x, y, z);
			if (!ConfigHandler.noRelightEnabled) {
				int meta = world.getBlockMetadata(x, y, z);
				world.setBlock(x, y, z, RealisticTorchesBlocks.torchUnlit, meta, 2);
			} else {
				world.setBlockToAir(x, y, z);
			}
		} else if (world.getBlock(x, y, z) == RealisticTorchesBlocks.torchLit) {
			world.setBlock(x, y, z, RealisticTorchesBlocks.torchSmoldering, world.getBlockMetadata(x, y, z), 2);
		}
	}

	public static boolean lightTorch(World world, int x, int y, int z, EntityPlayer player) {
		ItemStack stack = player.getHeldItem();

		if (stack != null && (stack.getItem() == Items.flint_and_steel || (ConfigHandler.matchboxCreatesFire && stack.getItem() == RealisticTorchesItems.matchbox))) {
			if (ConfigHandler.noRelightEnabled || world.getBlock(x, y, z) == RealisticTorchesBlocks.torchUnlit) {
				stack.damageItem(1, player);
				playTorchSound(world, x, y, z);

				if (!world.canLightningStrikeAt(x, y, z)) {
					world.setBlock(x, y, z, RealisticTorchesBlocks.torchLit, world.getBlockMetadata(x, y, z), 2);
				}

				return true;
			} else if (world.getBlock(x, y, z) == RealisticTorchesBlocks.torchLit) {
				stack.damageItem(1, player);
				world.setBlock(x, y, z, RealisticTorchesBlocks.torchLit, world.getBlockMetadata(x, y, z), 2);
				playTorchSound(world, x, y, z);

				return true;
			} else if (world.getBlock(x, y, z) == RealisticTorchesBlocks.torchSmoldering) {
				stack.damageItem(1, player);

				if (!world.canLightningStrikeAt(x, y, z)) {
					world.setBlock(x, y, z, RealisticTorchesBlocks.torchLit, world.getBlockMetadata(x, y, z), 2);
					playTorchSound(world, x, y, z);

					return true;
				}
			}
		}
		
		return false;
	}
	
	public static void updateTorch(World world, int x, int y, int z) {
		if (world.canLightningStrikeAt(x, y, z)) {
			extinguishTorch(world, x, y, z, true);
		}
	}
	
	public static void playTorchSound(World world, int x, int y, int z) {
		world.playSoundEffect(x, y, z, "random.fizz", 1.0F, world.rand.nextFloat() * 0.1F + 0.9F);
	}

}
