package com.chaosthedude.realistictorches.blocks;

import java.util.Random;

import com.chaosthedude.realistictorches.RealisticTorches;
import com.chaosthedude.realistictorches.config.ConfigHandler;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTorchSmoldering extends BlockRealisticTorch {

	public static final String NAME = "torch_smoldering";

	public BlockTorchSmoldering() {
		setUnlocalizedName(RealisticTorches.MODID + "." + NAME);
		setLightLevel(0.65F);
		setTickRandomly(true);
		setCreativeTab(null);
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		if (world.isRainingAt(pos)) {
			extinguish(world, pos, true);
		} else if (ConfigHandler.torchBurnout > 0) {
			world.scheduleUpdate(pos, this, (int) (ConfigHandler.torchBurnout / 10));
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
		if (!ConfigHandler.noRelightEnabled) {
			world.setBlockState(pos, getState(world, pos, RealisticTorchesBlocks.torchUnlit), 2);
		} else {
			world.setBlockToAir(pos);
		}
	}

	@Override
	public boolean isLit() {
		return true;
	}

	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
		final EnumFacing facing = state.getValue(FACING);
		final double d0 = (double) pos.getX() + 0.5D;
		final double d1 = (double) pos.getY() + 0.7D;
		final double d2 = (double) pos.getZ() + 0.5D;
		final double d3 = 0.22D;
		final double d4 = 0.27D;
		final int r = rand.nextInt(4);

		if (facing.getAxis().isHorizontal()) {
			EnumFacing facing1 = facing.getOpposite();
			world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4 * (double) facing1.getFrontOffsetX(), d1 + d3, d2 + d4 * (double) facing1.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D, new int[0]);
			if (r == 2) {
				world.spawnParticle(EnumParticleTypes.FLAME, d0 + d4 * (double) facing1.getFrontOffsetX(), d1 + d3, d2 + d4 * (double) facing1.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D, new int[0]);
			}
		} else {
			if (r == 2) {
				world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
			}
			world.spawnParticle(EnumParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
		}
	}

}
