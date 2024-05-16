package com.chaosthedude.realistictorches.items;

import com.chaosthedude.realistictorches.config.ConfigHandler;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;

public class MatchboxItem extends FlintAndSteelItem {

	public static final String NAME = "matchbox";

	public MatchboxItem(int maxDamage) {
		super(new Item.Properties().durability(maxDamage > 0 ? maxDamage : 0).durability(maxDamage));
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		if (!ConfigHandler.matchboxCreatesFire.get()) {
			return InteractionResult.FAIL;
		}

		return super.useOn(context);
	}

	@Override
	public boolean hasCraftingRemainingItem(ItemStack stack) {
		return true;
	}

	@Override
	public ItemStack getCraftingRemainingItem(ItemStack stack) {
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
