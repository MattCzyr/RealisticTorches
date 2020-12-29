package com.chaosthedude.realistictorches.blocks;

import com.chaosthedude.realistictorches.config.ConfigHandler;
import com.chaosthedude.realistictorches.items.RealisticTorchesItems;

import net.minecraft.block.BlockTorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockRealisticTorch extends BlockTorch {

	private boolean isLit;

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float side, float hitX, float hitY) {
		return lightTorch(world, pos, player, player.getHeldItem(hand));
	}

	public void extinguish(World world, BlockPos pos, boolean extinguishFully) {
		
	}

	public boolean lightTorch(World world, BlockPos pos, EntityPlayer player, ItemStack heldItem) {
		if (world.getBlockState(pos).getBlock() instanceof BlockRealisticTorch) {
			if (!heldItem.isEmpty() && (heldItem.getItem() == Items.FLINT_AND_STEEL || (ConfigHandler.matchboxCreatesFire && heldItem.getItem() == RealisticTorchesItems.matchbox)) && (!ConfigHandler.noRelightEnabled || !isLit())) {
				heldItem.damageItem(1, player);
				playIgniteSound(world, pos);
				if (!world.isRainingAt(pos)) {
					world.setBlockState(pos, getState(world, pos, RealisticTorchesBlocks.torchLit), 2);
				}

				return true;
			}
		}

		return false;
	}

	public void updateTorch(World world, BlockPos pos) {
		if (canBurnout() && world.isRainingAt(pos) && isLit()) {
			extinguish(world, pos, true);
		}
	}

	public IBlockState getState(World world, BlockPos pos, BlockTorch torch) {
		return torch.getStateFromMeta(torch.getMetaFromState(world.getBlockState(pos)));
	}

	public void playIgniteSound(World world, BlockPos pos) {
		world.playSound(null, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, world.rand.nextFloat() * 0.1F + 0.9F);
	}

	public void playExtinguishSound(World world, BlockPos pos) {
		world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, world.rand.nextFloat() * 0.1F + 0.9F);
	}

	public BlockRealisticTorch setLit(boolean lit) {
		isLit = lit;
		return this;
	}

	public boolean isLit() {
		return isLit;
	}
	
	public boolean canBurnout() {
		return ConfigHandler.torchBurnout > 0;
	}

}
