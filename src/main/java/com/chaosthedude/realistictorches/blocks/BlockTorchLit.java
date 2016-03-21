package com.chaosthedude.realistictorches.blocks;

import java.util.Random;

import com.chaosthedude.realistictorches.RealisticTorches;
import com.chaosthedude.realistictorches.RealisticTorchesBlocks;
import com.chaosthedude.realistictorches.blocks.te.TETorch;
import com.chaosthedude.realistictorches.config.ConfigHandler;

import net.minecraft.block.BlockTorch;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockTorchLit extends BlockTorch implements ITileEntityProvider {

	public static final String name = "TorchLit";

	public BlockTorchLit() {
		setUnlocalizedName(RealisticTorches.MODID + "_" + name);
		setLightLevel(0.9375F);
		setTickRandomly(false);
		setCreativeTab(CreativeTabs.tabDecorations);
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		if (world.isRainingAt(pos)) {
			world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.block_fire_extinguish, SoundCategory.BLOCKS, 1.0F, world.rand.nextFloat() * 0.1F + 0.9F, true);
			world.setBlockState(pos, RealisticTorchesBlocks.torchUnlit.getStateFromMeta(getMetaFromState(world.getBlockState(pos))));
		} else if (ConfigHandler.torchBurnout > 0) {
			world.scheduleUpdate(pos, this, (int) (ConfigHandler.torchBurnout * 0.9));
		}
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		world.setBlockState(pos, RealisticTorchesBlocks.torchSmoldering.getStateFromMeta(getMetaFromState(world.getBlockState(pos))), 2);
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

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TETorch();
	}

}