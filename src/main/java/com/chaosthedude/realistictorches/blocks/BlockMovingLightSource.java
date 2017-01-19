package com.chaosthedude.realistictorches.blocks;

import com.chaosthedude.realistictorches.RealisticTorches;
import com.chaosthedude.realistictorches.blocks.te.TEMovingLightSource;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMovingLightSource extends BlockAir implements ITileEntityProvider {

	public static final String NAME = "moving_light_source";

	private EntityPlayer player;

	public BlockMovingLightSource() {
		super();
		setUnlocalizedName(RealisticTorches.MODID + "." + NAME);
		setLightLevel(0.9F);
	}

	@Override
	public boolean isReplaceable(IBlockAccess world, BlockPos pos) {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TEMovingLightSource().setPlayer(player);
	}

	public BlockMovingLightSource setPlayer(EntityPlayer player) {
		this.player = player;
		return this;
	}

}
