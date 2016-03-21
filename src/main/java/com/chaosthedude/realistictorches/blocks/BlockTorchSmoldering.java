package com.chaosthedude.realistictorches.blocks;

import java.util.Random;

import com.chaosthedude.realistictorches.RealisticTorches;
import com.chaosthedude.realistictorches.RealisticTorchesBlocks;
import com.chaosthedude.realistictorches.blocks.te.TETorch;
import com.chaosthedude.realistictorches.config.ConfigHandler;

import net.minecraft.block.BlockTorch;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTorchSmoldering extends BlockTorch implements ITileEntityProvider {

	public static final String name = "TorchSmoldering";

	public BlockTorchSmoldering() {
		setUnlocalizedName(RealisticTorches.MODID + "_" + name);
		setLightLevel(0.51F);
		setTickRandomly(false);
		setCreativeTab(null);
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		if (world.isRainingAt(pos)) {
			world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.block_fire_extinguish, SoundCategory.BLOCKS,
					1.0F, world.rand.nextFloat() * 0.1F + 0.9F, true);
			world.setBlockState(pos, RealisticTorchesBlocks.torchUnlit.getStateFromMeta(getMetaFromState(world.getBlockState(pos))));
		} else if (ConfigHandler.torchBurnout > 0) {
			world.scheduleUpdate(pos, this, (int) (ConfigHandler.torchBurnout / 10));
		}
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.block_fire_extinguish, SoundCategory.BLOCKS, 1.0F, world.rand.nextFloat() * 0.1F + 0.9F, true);

		if (!ConfigHandler.noRelightEnabled) {
			world.setBlockState(pos,
					RealisticTorchesBlocks.torchUnlit.getStateFromMeta(getMetaFromState(world.getBlockState(pos))), 2);
		} else {
			world.setBlockToAir(pos);
		}
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!ConfigHandler.noRelightEnabled) {
			if (heldItem != null && heldItem.getItem() == Items.flint_and_steel) {
				heldItem.damageItem(1, player);
				world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.item_flintandsteel_use, SoundCategory.BLOCKS, 1.0F, world.rand.nextFloat() * 0.1F + 0.9F, true);

				if (!world.isRainingAt(pos)) {
					world.setBlockState(pos, RealisticTorchesBlocks.torchLit.getStateFromMeta(getMetaFromState(world.getBlockState(pos))), 2);
				}
			}

			return true;
		}

		return false;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random random, int fortune) {
		if (!ConfigHandler.noRelightEnabled) {
			return ItemBlock.getItemFromBlock(RealisticTorchesBlocks.torchUnlit);
		}

		return null;
	}

	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState worldIn, World pos, BlockPos state, Random rand) {
		EnumFacing facing = (EnumFacing) worldIn.getValue(FACING);
		double d0 = (double) state.getX() + 0.5D;
		double d1 = (double) state.getY() + 0.7D;
		double d2 = (double) state.getZ() + 0.5D;
		double d3 = 0.22D;
		double d4 = 0.27D;

		int r = rand.nextInt(4);

		if (facing.getAxis().isHorizontal()) {
			EnumFacing facing1 = facing.getOpposite();
			pos.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4 * (double) facing1.getFrontOffsetX(), d1 + d3,
					d2 + d4 * (double) facing1.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D, new int[0]);
			if (r == 2) {
				pos.spawnParticle(EnumParticleTypes.FLAME, d0 + d4 * (double) facing1.getFrontOffsetX(), d1 + d3,
						d2 + d4 * (double) facing1.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D, new int[0]);
			}
		} else {
			if (r == 2) {
				pos.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
			}
			pos.spawnParticle(EnumParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TETorch();
	}

}