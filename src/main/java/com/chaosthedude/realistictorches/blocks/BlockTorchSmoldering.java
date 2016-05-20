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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTorchSmoldering extends BlockTorch implements ITileEntityProvider {

	public static final String NAME = "TorchSmoldering";

	public BlockTorchSmoldering() {
		setUnlocalizedName(RealisticTorches.MODID + "_" + NAME);
		setLightLevel(0.65F);
		setTickRandomly(false);
		setCreativeTab(null);
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		if (world.canLightningStrike(pos)) {
			TorchHandler.extinguishTorch(world, pos, true);
		} else if (ConfigHandler.torchBurnout > 0) {
			world.scheduleUpdate(pos, this, (int) (ConfigHandler.torchBurnout / 10));
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
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		EnumFacing facing = (EnumFacing) state.getValue(FACING);
		double d0 = (double) pos.getX() + 0.5D;
		double d1 = (double) pos.getY() + 0.7D;
		double d2 = (double) pos.getZ() + 0.5D;
		double d3 = 0.22D;
		double d4 = 0.27D;

		int r = rand.nextInt(4);

		if (facing.getAxis().isHorizontal()) {
			EnumFacing enumfacing1 = facing.getOpposite();
			worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4 * (double) enumfacing1.getFrontOffsetX(), d1 + d3, d2 + d4 * (double) enumfacing1.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D, new int[0]);
			if (r == 2) {
				worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4 * (double) enumfacing1.getFrontOffsetX(), d1 + d3, d2 + d4 * (double) enumfacing1.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D, new int[0]);
			}
		} else {
			worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
			if (r == 2) {
				worldIn.spawnParticle(EnumParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
			}
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TETorch();
	}

}