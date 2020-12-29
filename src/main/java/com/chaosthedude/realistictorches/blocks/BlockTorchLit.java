package com.chaosthedude.realistictorches.blocks;

import java.util.Random;

import com.chaosthedude.realistictorches.RealisticTorches;
import com.chaosthedude.realistictorches.config.ConfigHandler;
import com.chaosthedude.realistictorches.items.RealisticTorchesItems;

import net.minecraft.block.BlockTorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
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
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float side, float hitX, float hitY) {
		if (player.getHeldItem(hand).getItem() == RealisticTorchesItems.glowstonePaste) {
			world.setBlockState(pos, getState(world, pos, (BlockTorch) Blocks.TORCH));
			if (!player.isCreative()) {
				player.getHeldItem(hand).shrink(1);
			}
			return true;
		}

		return super.onBlockActivated(world, pos, state, player, hand, facing, side, hitX, hitY);
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		if (canBurnout()) {
			if (world.isRainingAt(pos)) {
				extinguish(world, pos, true);
			} else {
				world.scheduleUpdate(pos, this, (int) (ConfigHandler.torchBurnout * 0.9));
			}
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
		} else if (!ConfigHandler.noRelightEnabled) {
			world.setBlockState(pos, getState(world, pos, RealisticTorchesBlocks.torchUnlit), 2);
		} else {
			world.setBlockToAir(pos);
		}
	}

}
