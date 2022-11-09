package com.chaosthedude.realistictorches.items;

import com.chaosthedude.realistictorches.config.ConfigHandler;

import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;

public class MatchboxItem extends FlintAndSteelItem {

	public static final String NAME = "matchbox";

	public MatchboxItem(int maxDamage) {
		super(new Item.Properties().tab(ItemGroup.TAB_TOOLS).durability(maxDamage > 0 ? maxDamage : 0).defaultDurability(maxDamage).setNoRepair());
	}

	@Override
	public ActionResultType useOn(ItemUseContext context) {
		if (!ConfigHandler.matchboxCreatesFire.get()) {
			return ActionResultType.FAIL;
		}

		return super.useOn(context);
	}

	@Override
	public boolean hasContainerItem(ItemStack stack) {
		return true;
	}

	@Override
	public ItemStack getContainerItem(ItemStack stack) {
		if (ConfigHandler.matchboxDurability.get() <= 0) {
			return new ItemStack(this);
		} else if (stack.getDamageValue() + 1 > stack.getMaxDamage()) {
			return ItemStack.EMPTY;
		}
		ItemStack newStack = new ItemStack(this);
		newStack.setDamageValue(stack.getDamageValue() + 1);
		return newStack;
	}
}
