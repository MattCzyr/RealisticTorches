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

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTorchSmoldering extends BlockTorch {

	public static final String name = "TorchSmoldering";
	
	public BlockTorchSmoldering() {
		this.setBlockName(RealisticTorches.ID + "_" + name);
		this.setBlockTextureName(RealisticTorches.ID + ":" + name);
		this.setLightLevel(0.51F);
		this.setTickRandomly(false);
		this.setCreativeTab(null);
	}
	
	public int damageDropped(int meta) {
        return meta;
    }
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
			world.scheduleBlockUpdate(x, y, z, this, (int)(ConfigHandler.torchBurnout / 10));
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		int meta = world.getBlockMetadata(x, y, z);
		world.setBlock(x, y, z, BlockRegistry.torchUnlit, meta, 2);
		world.playSoundEffect(x, y, z, "random.fizz", 1.0F, world.rand.nextFloat() * 0.1F + 0.9F);
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
	
	@Override
	@SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		int r = rand.nextInt(4);
        int l = world.getBlockMetadata(x, y, z);
        double d0 = (double)((float)x + 0.5F);
        double d1 = (double)((float)y + 0.7F);
        double d2 = (double)((float)z + 0.5F);
        double d3 = 0.2199999988079071D;
        double d4 = 0.27000001072883606D;

        if (l == 1) {
        	world.spawnParticle("smoke", d0 - d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D);
        	if (r == 2) {
        		world.spawnParticle("flame", d0 - d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D);
        	}
        }
        
        else if (l == 2) {
        	world.spawnParticle("smoke", d0 + d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D);
        	if (r == 2) {
        		world.spawnParticle("flame", d0 + d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D);
        	}
        }
        
        else if (l == 3) {
        	world.spawnParticle("smoke", d0, d1 + d3, d2 - d4, 0.0D, 0.0D, 0.0D);
        	if (r == 2) {
        		world.spawnParticle("flame", d0, d1 + d3, d2 - d4, 0.0D, 0.0D, 0.0D);
        	}
        }
        
        else if (l == 4) {
        	world.spawnParticle("smoke", d0, d1 + d3, d2 + d4, 0.0D, 0.0D, 0.0D);
        	if (r == 2) {
        		world.spawnParticle("flame", d0, d1 + d3, d2 + d4, 0.0D, 0.0D, 0.0D);
        	}
        }
        
        else {
        	world.spawnParticle("smoke", d0, d1, d2, 0.0D, 0.0D, 0.0D);
        	if (r == 2) {
        		world.spawnParticle("flame", d0, d1, d2, 0.0D, 0.0D, 0.0D);
        	}
        }
    }
	
}