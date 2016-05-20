package com.chaosthedude.realistictorches.items;

import java.util.List;

import com.chaosthedude.realistictorches.RealisticTorches;
import com.chaosthedude.realistictorches.config.ConfigHandler;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemMatchbox extends Item {

	public static final String NAME = "Matchbox";

	public ItemMatchbox() {
		super();
		setUnlocalizedName(RealisticTorches.MODID + "_" + NAME);
		setTextureName(RealisticTorches.MODID + ":" + NAME);
		setMaxStackSize(1);
		setMaxDamage(ConfigHandler.matchboxDurability - 1);
		setCreativeTab(CreativeTabs.tabTools);
		setNoRepair();
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if (!ConfigHandler.matchboxCreatesFire) {
			return false;
		}

		if (side == 0) {
			--y;
		}

		if (side == 1) {
			++y;
		}

		if (side == 2) {
			--z;
		}

		if (side == 3) {
			++z;
		}

		if (side == 4) {
			--x;
		}

		if (side == 5) {
			++x;
		}

		if (!player.canPlayerEdit(x, y, z, side, stack)) {
			return false;
		} else {
			if (world.isAirBlock(x, y, z)) {
				world.playSoundEffect((double) x + 0.5D, (double) y + 0.5D, (double) z + 0.5D, "fire.ignite", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
				world.setBlock(x, y, z, Blocks.fire);
			}

			stack.damageItem(8, player);
			return true;
		}
	}
	@Override
	public boolean hasContainerItem() {
		return true;
	}

	@Override
	public boolean doesContainerItemLeaveCraftingGrid(ItemStack stack) {
		return false;
	}

	@Override
	public ItemStack getContainerItem(ItemStack stack) {
		if (ConfigHandler.matchboxDurability > 1) {
			int dmg = stack.getItemDamage();
			ItemStack newStack = stack.copy();
			newStack.setItemDamage(dmg + 1);
			return newStack;
		}

		return stack;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List info, boolean par4) {
		if (GuiScreen.isShiftKeyDown()) {
			info.add(EnumChatFormatting.ITALIC + "It's lit");
		}
	}

}
