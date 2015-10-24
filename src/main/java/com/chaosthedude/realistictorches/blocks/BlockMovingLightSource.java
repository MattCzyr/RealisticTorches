package com.chaosthedude.realistictorches.blocks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import com.chaosthedude.realistictorches.RealisticTorches;
import com.chaosthedude.realistictorches.blocks.tileentities.TileEntityMovingLightSource;

import cpw.mods.fml.common.registry.GameData;

public class BlockMovingLightSource extends Block implements ITileEntityProvider
{
	
    public static List<Item> lightSourceList = new ArrayList<Item>() {
    	{
    		add(Item.getItemFromBlock(Blocks.torch));
            add(Item.getItemFromBlock(Blocks.redstone_torch));
            add(Item.getItemFromBlock(Blocks.redstone_lamp));
            add(Item.getItemFromBlock(Blocks.redstone_block));
            add(Item.getItemFromBlock(Blocks.redstone_ore));
            add(Item.getItemFromBlock(Blocks.glowstone));
            add(Items.glowstone_dust);
            add(Item.getItemFromBlock(Blocks.lava));
            add(Items.lava_bucket);
            add(Item.getItemFromBlock(Blocks.lit_redstone_lamp));
            add(Item.getItemFromBlock(Blocks.beacon));
            add(Item.getItemFromBlock(Blocks.end_portal));
            add(Item.getItemFromBlock(Blocks.end_portal_frame));
    	}
    };
    
    public static final String name = "MovingLightSource";

    public BlockMovingLightSource() {    	
        super(Material.air);
        this.setBlockName(RealisticTorches.ID + "_" + name);
        this.setTickRandomly(false);
        this.setLightLevel(0.9F);
        this.setBlockBounds(0.5F, 0.5F, 0.5F, 0.5F, 0.5F, 0.5F);
    }
    
    public static boolean isLightEmittingItem(Item parItem) {
        return lightSourceList.contains(parItem);
    }
    
    @Override
    public int getRenderType() {
        return -1;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return null;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, int x, int y, int z) {
        return true;
    }

    @Override
    public void onBlockAdded(World worldIn, int x, int y, int z) {
        return;
    }
    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock) {
        return;
    }

    @Override
    public void onFallenUpon(World worldIn, int x, int y, int z, Entity entityIn, float fallDistance) {
        return;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityMovingLightSource();
    }
    
}