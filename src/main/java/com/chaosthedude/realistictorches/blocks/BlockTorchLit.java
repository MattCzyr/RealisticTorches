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
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockTorchLit extends BlockTorch implements ITileEntityProvider {

	public static final String NAME = "TorchLit";

	public BlockTorchLit() {
		setUnlocalizedName(RealisticTorches.MODID + "_" + NAME);
		setLightLevel(0.9375F);
		setTickRandomly(false);
		setCreativeTab(CreativeTabs.tabDecorations);
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		if (world.canLightningStrike(pos)) {
			TorchHandler.extinguishTorch(world, pos, true);
		} else if (ConfigHandler.torchBurnout > 0) {
			world.scheduleUpdate(pos, this, (int) (ConfigHandler.torchBurnout * 0.9));
		}
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		TorchHandler.extinguishTorch(world, pos, false);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		return TorchHandler.lightTorch(world, pos, player, player.getHeldItem());
	}

	@Override
	public Item getItemDropped(IBlockState state, Random random, int fortune) {
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