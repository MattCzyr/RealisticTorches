package com.chaosthedude.realistictorches.blocks;

import java.util.Random;

import com.chaosthedude.realistictorches.RealisticTorches;
import com.chaosthedude.realistictorches.RealisticTorchesBlocks;
import com.chaosthedude.realistictorches.RealisticTorchesItems;
import com.chaosthedude.realistictorches.blocks.te.TETorch;
import com.chaosthedude.realistictorches.config.ConfigHandler;
import com.chaosthedude.realistictorches.handler.TorchHandler;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockTorchSmoldering extends BlockTorch implements ITileEntityProvider {

	public static final String NAME = "TorchSmoldering";

	public BlockTorchSmoldering() {
		setBlockName(RealisticTorches.MODID + "_" + NAME);
		setBlockTextureName(RealisticTorches.MODID + ":" + NAME);
		setLightLevel(0.65F);
		setTickRandomly(false);
		setCreativeTab(null);
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		if (world.canLightningStrikeAt(x, y, z)) {
			TorchHandler.extinguishTorch(world, x, y, z, true);
		} else if (ConfigHandler.torchBurnout > 0) {
			world.scheduleBlockUpdate(x, y, z, this, (int) (ConfigHandler.torchBurnout / 10));
		}
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		TorchHandler.extinguishTorch(world, x, y, z, false);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int metadata, float sideX, float sideY, float sideZ) {
		return TorchHandler.lightTorch(world, x, y, z, player);
	}

	@Override
	public Item getItemDropped(int meta, Random random, int fortune) {
		if (!ConfigHandler.noRelightEnabled) {
			return ItemBlock.getItemFromBlock(RealisticTorchesBlocks.torchUnlit);
		}

		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		int r = rand.nextInt(4);
		int l = world.getBlockMetadata(x, y, z);
		double d0 = (double) ((float) x + 0.5F);
		double d1 = (double) ((float) y + 0.7F);
		double d2 = (double) ((float) z + 0.5F);
		double d3 = 0.2199999988079071D;
		double d4 = 0.27000001072883606D;

		if (l == 1) {
			world.spawnParticle("smoke", d0 - d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D);

			if (r == 2) {
				world.spawnParticle("flame", d0 - d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D);
			}
		}

		else if (l == 2) {
			world.spawnParticle("smoke", d0 + d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D);

			if (r == 2) {
				world.spawnParticle("flame", d0 + d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D);
			}
		} else if (l == 3) {
			world.spawnParticle("smoke", d0, d1 + d3, d2 - d4, 0.0D, 0.0D, 0.0D);

			if (r == 2) {
				world.spawnParticle("flame", d0, d1 + d3, d2 - d4, 0.0D, 0.0D, 0.0D);
			}
		} else if (l == 4) {
			world.spawnParticle("smoke", d0, d1 + d3, d2 + d4, 0.0D, 0.0D, 0.0D);

			if (r == 2) {
				world.spawnParticle("flame", d0, d1 + d3, d2 + d4, 0.0D, 0.0D, 0.0D);
			}
		} else {
			world.spawnParticle("smoke", d0, d1, d2, 0.0D, 0.0D, 0.0D);

			if (r == 2) {
				world.spawnParticle("flame", d0, d1, d2, 0.0D, 0.0D, 0.0D);
			}
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TETorch();
	}

}