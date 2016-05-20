package com.chaosthedude.realistictorches.blocks;

import java.util.Random;

import com.chaosthedude.realistictorches.RealisticTorches;
import com.chaosthedude.realistictorches.RealisticTorchesBlocks;
import com.chaosthedude.realistictorches.RealisticTorchesItems;
import com.chaosthedude.realistictorches.blocks.te.TETorch;
import com.chaosthedude.realistictorches.config.ConfigHandler;
import com.chaosthedude.realistictorches.handler.TorchHandler;

import net.minecraft.block.BlockTorch;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockTorchLit extends BlockTorch implements ITileEntityProvider {

	public static final String NAME = "TorchLit";

	public BlockTorchLit() {
		setBlockName(RealisticTorches.MODID + "_" + NAME);
		setBlockTextureName(RealisticTorches.MODID + ":" + NAME);
		setLightLevel(0.9375F);
		setTickRandomly(false);
		setCreativeTab(CreativeTabs.tabDecorations);
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		if (world.canLightningStrikeAt(x, y, z)) {
			TorchHandler.extinguishTorch(world, x, y, z, true);
		} else if (ConfigHandler.torchBurnout > 0) {
			world.scheduleBlockUpdate(x, y, z, this, (int) (ConfigHandler.torchBurnout * 0.9));
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
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TETorch();
	}

}