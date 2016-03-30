package com.chaosthedude.realistictorches.items;

import com.chaosthedude.realistictorches.RealisticTorches;
import com.chaosthedude.realistictorches.config.ConfigHandler;
import com.chaosthedude.realistictorches.util.Util;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemMatchbox extends Item {

	public static final String name = "Matchbox";

	public ItemMatchbox() {
		super();
		setUnlocalizedName(RealisticTorches.MODID + "_" + name);
		setMaxStackSize(1);
		setMaxDamage(ConfigHandler.matchboxDurability - 1);
		setCreativeTab(CreativeTabs.tabTools);
		setNoRepair();
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!ConfigHandler.matchboxCreatesFire) {
			return EnumActionResult.FAIL;
		}
		
		pos = pos.offset(facing);

		if (!player.canPlayerEdit(pos, facing, stack)) {
			return EnumActionResult.FAIL;
		} else {
			if (world.isAirBlock(pos)) {
				world.playSound(player, pos, SoundEvents.item_flintandsteel_use, SoundCategory.BLOCKS, 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
				world.setBlockState(pos, Blocks.fire.getDefaultState(), 11);
			}

			stack.damageItem(1, player);
			return EnumActionResult.SUCCESS;
		}
	}

	@Override
	public boolean hasContainerItem() {
		return true;
	}

	@Override
	public ItemStack getContainerItem(ItemStack stack) {
		if (ConfigHandler.matchboxDurability > 1) {
			ItemStack newStack = Util.copyStack(stack, 1);
			newStack.setItemDamage(stack.getItemDamage() + 1);
			return newStack;
		}

		return stack;
	}

}
