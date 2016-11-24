package com.chaosthedude.realistictorches.blocks;

import java.util.Random;

import com.chaosthedude.realistictorches.RealisticTorches;
import com.chaosthedude.realistictorches.config.ConfigHandler;

import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockTorchLit extends BlockRealisticTorch {

	public static final String NAME = "torch_lit";

	public BlockTorchLit() {
		setUnlocalizedName(RealisticTorches.MODID + "." + NAME);
		setLightLevel(0.9375F);
		setTickRandomly(true);
		setLit(true);
		setCreativeTab(CreativeTabs.DECORATIONS);
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		if (world.isRainingAt(pos)) {
			extinguish(world, pos, true);
		} else if (ConfigHandler.torchBurnout > 0) {
			world.scheduleUpdate(pos, this, (int) (ConfigHandler.torchBurnout * 0.9));
		}
	}

	@Override
	public void randomTick(World world, BlockPos pos, IBlockState state, Random random) {
		updateTorch(world, pos);
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		extinguish(world, pos, false);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random random, int fortune) {
		if (!ConfigHandler.noRelightEnabled) {
			return Item.getItemFromBlock(RealisticTorchesBlocks.torchUnlit);
		}

		return null;
	}
	
	@Override
	public void extinguish(World world, BlockPos pos, boolean extinguishFully) {
		playExtinguishSound(world, pos);
		if (!extinguishFully) {
			world.setBlockState(pos, getState(world, pos, RealisticTorchesBlocks.torchSmoldering), 2);
		} else {
			world.setBlockState(pos, getState(world, pos, RealisticTorchesBlocks.torchUnlit), 2);
		}
	}

}
