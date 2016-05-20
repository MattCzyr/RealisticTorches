package com.chaosthedude.realistictorches.blocks;

import java.util.Random;

import com.chaosthedude.realistictorches.RealisticTorches;
import com.chaosthedude.realistictorches.RealisticTorchesBlocks;
import com.chaosthedude.realistictorches.RealisticTorchesItems;
import com.chaosthedude.realistictorches.config.ConfigHandler;
import com.chaosthedude.realistictorches.handler.TorchHandler;

import net.minecraft.block.BlockTorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTorchUnlit extends BlockTorch {

	public static final String NAME = "TorchUnlit";

	public BlockTorchUnlit() {
		setUnlocalizedName(RealisticTorches.MODID + "_" + NAME);
		setCreativeTab(CreativeTabs.DECORATIONS);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		return TorchHandler.lightTorch(world, pos, player, heldItem);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
		if (!ConfigHandler.unlitParticlesEnabled) {
			return;
		}

		EnumFacing facing = (EnumFacing) state.getValue(FACING);
		double d0 = (double) pos.getX() + 0.5D;
		double d1 = (double) pos.getY() + 0.7D;
		double d2 = (double) pos.getZ() + 0.5D;
		double d3 = 0.22D;
		double d4 = 0.27D;

		if (facing.getAxis().isHorizontal()) {
			EnumFacing facing1 = facing.getOpposite();
			world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4 * (double) facing1.getFrontOffsetX(), d1 + d3, d2 + d4 * (double) facing1.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D, new int[0]);
		} else {
			world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
		}
	}

}