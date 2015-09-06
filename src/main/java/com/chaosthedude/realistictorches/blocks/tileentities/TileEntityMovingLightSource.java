package com.chaosthedude.realistictorches.blocks.tileentities;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.chaosthedude.realistictorches.blocks.BlockMovingLightSource;
import com.chaosthedude.realistictorches.blocks.BlockRegistry;

public class TileEntityMovingLightSource extends TileEntity
{
    public EntityPlayer thePlayer;
    
    public TileEntityMovingLightSource() {
    	
    }
    
    @Override
    public boolean shouldRefresh(Block oldBlock, Block newBlock, int oldMeta, int newMeta, World world, int x, int y, int z) {
        return (oldMeta != newMeta);
    }

    @Override
    public void updateEntity() {
        EntityPlayer thePlayer = worldObj.getClosestPlayer(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, 2.0D);
        if (thePlayer == null) {
            if (worldObj.getBlock(xCoord, yCoord, zCoord) == BlockRegistry.movingLightSource) {
                worldObj.setBlockToAir(xCoord, yCoord, zCoord);
            }
        }
        try {
        	if (!BlockMovingLightSource.isLightEmittingItem(thePlayer.getCurrentEquippedItem().getItem()) && thePlayer.getCurrentEquippedItem().getItem() != Item.getItemFromBlock(BlockRegistry.torchLit)) {
        		if (worldObj.getBlock(xCoord, yCoord, zCoord) == BlockRegistry.movingLightSource) {
        			worldObj.setBlockToAir(xCoord, yCoord, zCoord);
        		}            
        	}
        }
        
        catch(NullPointerException e) {
        	if (worldObj.getBlock(xCoord, yCoord, zCoord) == BlockRegistry.movingLightSource) {
        		worldObj.setBlockToAir(xCoord, yCoord, zCoord);
        	}
    	}
    }  
    
    public void setPlayer(EntityPlayer parPlayer) {
        thePlayer = parPlayer;
    }
    
}