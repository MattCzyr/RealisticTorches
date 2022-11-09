package com.chaosthedude.realistictorches.conditions;

import com.chaosthedude.realistictorches.RealisticTorches;

import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class RealisticTorchesConditions {

	public static final LootConditionType DROP_UNLIT_TYPE = register(DropUnlitCondition.NAME, new DropUnlitCondition.Serializer());

	public static LootConditionType register(String resourceName, ILootSerializer<? extends ILootCondition> lootSerializer) {
		return Registry.register(Registry.LOOT_CONDITION_TYPE, new ResourceLocation(RealisticTorches.MODID, resourceName), new LootConditionType(lootSerializer));
	}

}
