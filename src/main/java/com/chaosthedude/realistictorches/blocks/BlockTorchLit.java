package com.chaosthedude.realistictorches.blocks;

import java.util.Random;

import com.chaosthedude.realistictorches.RealisticTorches;
import com.chaosthedude.realistictorches.RealisticTorchesBlocks;
import com.chaosthedude.realistictorches.blocks.te.TETorch;
import com.chaosthedude.realistictorches.config.ConfigHandler;

import net.minecraft.block.BlockTorch;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockTorchLit extends BlockTorch implements ITileEntityProvider {

	public static final String name = "TorchLit";

	public BlockTorchLit() {
		setBlockName(RealisticTorches.MODID + "_" + name);
		setBlockTextureName(RealisticTorches.MODID + ":" + name);
		setLightLevel(0.9375F);
		setTickRandomly(false);
		setCreativeTab(CreativeTabs.tabDecorations);
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		world.scheduleBlockUpdate(x, y, z, this, (int) (ConfigHandler.torchBurnout * 0.9));
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		int meta = world.getBlockMetadata(x, y, z);
		world.setBlock(x, y, z, RealisticTorchesBlocks.torchSmoldering, meta, 2);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int metadata, float sideX, float sideY, float sideZ) {
		if (!ConfigHandler.noRelightEnabled) {
			int meta = world.getBlockMetadata(x, y, z);
			ItemStack itemStack = player.getCurrentEquippedItem();

			if (itemStack != null && itemStack.getItem() == Items.flint_and_steel) {
				itemStack.damageItem(1, player);
				world.setBlock(x, y, z, RealisticTorchesBlocks.torchLit, meta, 2);
				world.playSoundEffect(x, y, z, "random.fizz", 1.0F, world.rand.nextFloat() * 0.1F + 0.9F);
			}

			return true;
		} else {
			return false;
		}
	}

	@Override
	public Item getItemDropped(int meta, Random random, int fortune) {
		return ItemBlock.getItemFromBlock(RealisticTorchesBlocks.torchUnlit);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TETorch();
	}

}