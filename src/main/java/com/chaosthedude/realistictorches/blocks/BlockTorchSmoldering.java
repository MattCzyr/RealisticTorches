package com.chaosthedude.realistictorches.blocks;

import java.util.Random;

import com.chaosthedude.realistictorches.RealisticTorches;
import com.chaosthedude.realistictorches.config.ConfigHandler;
import com.chaosthedude.realistictorches.items.RealisticTorchesItems;

import net.minecraft.block.BlockTorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
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
				world.scheduleUpdate(pos, this, (int) (ConfigHandler.torchBurnout / 10));
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
		final double x = (double) pos.getX() + 0.5D;
		final double y = (double) pos.getY() + 0.7D;
		final double z = (double) pos.getZ() + 0.5D;
		final double mod1 = 0.22D;
		final double mod2 = 0.27D;
		final int r = rand.nextInt(4);

		if (facing.getAxis().isHorizontal()) {
			final EnumFacing opposite = facing.getOpposite();
			world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x + mod2 * (double) opposite.getFrontOffsetX(), y + mod1, z + mod2 * (double) opposite.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D);
			if (r == 2) {
				world.spawnParticle(EnumParticleTypes.FLAME, x + mod2 * (double) opposite.getFrontOffsetX(), y + mod1, z + mod2 * (double) opposite.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D);
			}
		} else {
			if (r == 2) {
				world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x, y, z, 0.0D, 0.0D, 0.0D);
			}
			world.spawnParticle(EnumParticleTypes.FLAME, x, y, z, 0.0D, 0.0D, 0.0D);
		}
	}

}
