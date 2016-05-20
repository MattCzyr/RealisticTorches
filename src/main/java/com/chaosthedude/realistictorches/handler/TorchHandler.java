package com.chaosthedude.realistictorches.handler;

import com.chaosthedude.realistictorches.RealisticTorchesBlocks;
import com.chaosthedude.realistictorches.RealisticTorchesItems;
import com.chaosthedude.realistictorches.config.ConfigHandler;

import net.minecraft.block.BlockTorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class TorchHandler {
	
	public static void extinguishTorch(World world, BlockPos pos, boolean extinguishFully) {
		if (world.getBlockState(pos).getBlock() instanceof BlockTorch) {
			BlockTorch torch = (BlockTorch) world.getBlockState(pos).getBlock();
			if (extinguishFully || torch == RealisticTorchesBlocks.torchSmoldering) {
				playTorchSound(world, pos);

				if (!ConfigHandler.noRelightEnabled) {
					world.setBlockState(pos, getState(world, pos, RealisticTorchesBlocks.torchUnlit), 2);
				} else {
					world.setBlockToAir(pos);
				}
			} else if (torch == RealisticTorchesBlocks.torchLit) {
				world.setBlockState(pos, getState(world, pos, RealisticTorchesBlocks.torchSmoldering), 2);
			}
		}
	}

	public static boolean lightTorch(World world, BlockPos pos, EntityPlayer player, ItemStack heldItem) {
		if (world.getBlockState(pos).getBlock() instanceof BlockTorch) {
			BlockTorch torch = (BlockTorch) world.getBlockState(pos).getBlock();
			if (heldItem != null && (heldItem.getItem() == Items.flint_and_steel || (ConfigHandler.matchboxCreatesFire && heldItem.getItem() == RealisticTorchesItems.matchbox))) {
				if (ConfigHandler.noRelightEnabled || torch == RealisticTorchesBlocks.torchUnlit) {
					heldItem.damageItem(1, player);
					playTorchSound(world, pos);

					if (!world.canLightningStrike(pos)) {
						world.setBlockState(pos, getState(world, pos, RealisticTorchesBlocks.torchLit), 2);
					}

					return true;
				} else if (torch == RealisticTorchesBlocks.torchLit) {
					heldItem.damageItem(1, player);
					playTorchSound(world, pos);

					if (!world.canLightningStrike(pos)) {
						world.setBlockState(pos, getState(world, pos, RealisticTorchesBlocks.torchLit), 2);
					}

					return true;
				} else if (torch == RealisticTorchesBlocks.torchSmoldering) {
					heldItem.damageItem(1, player);
					playTorchSound(world, pos);

					if (!world.canLightningStrike(pos)) {
						world.setBlockState(pos, getState(world, pos, RealisticTorchesBlocks.torchLit), 2);
					}

					return true;
				}
			}
		}

		return false;
	}

	public static void updateTorch(World world, BlockPos pos) {
		if (world.canLightningStrike(pos)) {
			world.setBlockState(pos, getState(world, pos, RealisticTorchesBlocks.torchUnlit), 2);
		}
	}
	
	public static IBlockState getState(World world, BlockPos pos, BlockTorch torch) {
		return torch.getStateFromMeta(torch.getMetaFromState(world.getBlockState(pos)));
	}
	
	public static void playTorchSound(World world, BlockPos pos) {
		world.playSoundEffect(pos.getX(), pos.getY(), pos.getZ(), "random.fizz", 1.0F, world.rand.nextFloat() * 0.1F + 0.9F);
	}

}
