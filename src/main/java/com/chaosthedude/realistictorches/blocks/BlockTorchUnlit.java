package com.chaosthedude.realistictorches.blocks;

import java.util.Random;

import com.chaosthedude.realistictorches.RealisticTorches;
import com.chaosthedude.realistictorches.RealisticTorchesBlocks;
import com.chaosthedude.realistictorches.config.ConfigHandler;

import net.minecraft.block.BlockTorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTorchUnlit extends BlockTorch {

	public static final String name = "TorchUnlit";

	public BlockTorchUnlit() {
		setUnlocalizedName(RealisticTorches.MODID + "_" + name);
		setCreativeTab(CreativeTabs.tabDecorations);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		int meta = this.getMetaFromState(world.getBlockState(pos));

		ItemStack itemStack = player.getCurrentEquippedItem();

		if (itemStack != null && itemStack.getItem() == Items.flint_and_steel && !world.canLightningStrike(pos)) {
			itemStack.damageItem(1, player);
			world.setBlockState(pos, RealisticTorchesBlocks.torchLit.getStateFromMeta(meta), 2);
			world.playSoundEffect(pos.getX(), pos.getY(), pos.getZ(), "random.fizz", 1.0F, world.rand.nextFloat() * 0.1F + 0.9F);
		}

		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (ConfigHandler.unlitParticlesEnabled == true) {
			EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);
			double d0 = (double) pos.getX() + 0.5D;
			double d1 = (double) pos.getY() + 0.7D;
			double d2 = (double) pos.getZ() + 0.5D;
			double d3 = 0.22D;
			double d4 = 0.27D;

			if (enumfacing.getAxis().isHorizontal()) {
				EnumFacing enumfacing1 = enumfacing.getOpposite();
				worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4 * (double) enumfacing1.getFrontOffsetX(), d1 + d3, d2 + d4 * (double) enumfacing1.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D, new int[0]);
			} else {
				worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
			}
		}
	}

}