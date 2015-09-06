package com.chaosthedude.realistictorches.blocks;

import java.util.Random;

import net.minecraft.block.BlockTorch;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.chaosthedude.realistictorches.RealisticTorches;
import com.chaosthedude.realistictorches.handlers.ConfigHandler;

public class BlockTorchLit extends BlockTorch {
	
	public static final String name = "TorchLit";
	
	public BlockTorchLit() {
		this.setBlockName(RealisticTorches.ID + "_" + name);
		this.setBlockTextureName(RealisticTorches.ID + ":" + name);
		this.setLightLevel(0.9375F);
		this.setTickRandomly(false);
		this.setCreativeTab(null);
	}
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
			world.scheduleBlockUpdate(x, y, z, this, (int)(ConfigHandler.torchBurnout * 0.9));
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		int meta = world.getBlockMetadata(x, y, z);
		world.setBlock(x, y, z, BlockRegistry.torchSmoldering, meta, 2);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int metadata, float sideX, float sideY, float sideZ) {
		int meta = world.getBlockMetadata(x, y, z);
		
		ItemStack itemStack = player.getCurrentEquippedItem();
		
		if (itemStack != null) {
			if (itemStack.getItem() == Items.flint_and_steel) {
				itemStack.damageItem(1, player);
				world.setBlock(x, y, z, BlockRegistry.torchLit, meta, 2);
				world.playSoundEffect(x, y, z, "random.fizz", 1.0F, world.rand.nextFloat() * 0.1F + 0.9F);
			}
		}
		
		return true;
	}
	
	
	@Override
	public Item getItemDropped(int meta, Random random, int fortune) {
		return ItemBlock.getItemFromBlock(BlockRegistry.torchUnlit);
	}
	
}