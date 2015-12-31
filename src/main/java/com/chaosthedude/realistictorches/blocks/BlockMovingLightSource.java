package com.chaosthedude.realistictorches.blocks;

import com.chaosthedude.realistictorches.RealisticTorches;
import com.chaosthedude.realistictorches.blocks.te.TEMovingLightSource;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockMovingLightSource extends Block implements ITileEntityProvider {

	public static final String name = "MovingLightSource";

	public BlockMovingLightSource() {
		super(Material.air);
		setBlockName(RealisticTorches.MODID + "_" + name);
		setTickRandomly(false);
		setLightLevel(0.9F);
		setBlockBounds(0.5F, 0.5F, 0.5F, 0.5F, 0.5F, 0.5F);
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
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TEMovingLightSource();
	}

}