package com.chaosthedude.realistictorches.blocks;

import java.util.Random;

import com.chaosthedude.realistictorches.RealisticTorches;
import com.chaosthedude.realistictorches.config.ConfigHandler;

import net.minecraft.block.BlockTorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTorchUnlit extends BlockRealisticTorch {

	public static final String NAME = "torch_unlit";

	public BlockTorchUnlit() {
		setUnlocalizedName(RealisticTorches.MODID + "." + NAME);
		setCreativeTab(CreativeTabs.DECORATIONS);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
		if (!ConfigHandler.unlitParticlesEnabled) {
			return;
		}

		final EnumFacing facing = state.getValue(FACING);
		final double d0 = (double) pos.getX() + 0.5D;
		final double d1 = (double) pos.getY() + 0.7D;
		final double d2 = (double) pos.getZ() + 0.5D;
		final double d3 = 0.22D;
		final double d4 = 0.27D;

		if (facing.getAxis().isHorizontal()) {
			final EnumFacing opposite = facing.getOpposite();
			world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4 * (double) opposite.getFrontOffsetX(), d1 + d3, d2 + d4 * (double) opposite.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D, new int[0]);
		} else {
			world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
		}
	}

}
