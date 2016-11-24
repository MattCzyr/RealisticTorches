package com.chaosthedude.realistictorches.items;

import java.util.List;

import com.chaosthedude.realistictorches.RealisticTorches;
import com.chaosthedude.realistictorches.config.ConfigHandler;
import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.gui.GuiScreen;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMatchbox extends Item {

	public static final String NAME = "matchbox";

	public ItemMatchbox() {
		super();
		setUnlocalizedName(RealisticTorches.MODID + "." + NAME);
		setMaxStackSize(1);
		setMaxDamage(ConfigHandler.matchboxDurability - 1);
		setCreativeTab(CreativeTabs.TOOLS);
		setNoRepair();
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!ConfigHandler.matchboxCreatesFire) {
			return EnumActionResult.FAIL;
		}

		pos = pos.offset(facing);

		if (!player.canPlayerEdit(pos, facing, player.getHeldItem(hand))) {
			return EnumActionResult.FAIL;
		} else {
			if (world.isAirBlock(pos)) {
				world.playSound(player, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
				world.setBlockState(pos, Blocks.FIRE.getDefaultState(), 11);
			}

			player.getHeldItem(hand).damageItem(1, player);
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
			ItemStack newStack = stack.copy();
			newStack.setItemDamage(stack.getItemDamage() + 1);
			return newStack;
		}

		return stack;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List info, boolean par4) {
		if (GuiScreen.isShiftKeyDown()) {
			info.add(ChatFormatting.ITALIC + "It's lit");
		}
	}

}
