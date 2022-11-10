package com.chaosthedude.realistictorches.conditions;

import com.chaosthedude.realistictorches.RealisticTorches;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.Serializer;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class RealisticTorchesConditions {

	public static final LootItemConditionType DROP_UNLIT_TYPE = register(DropUnlitCondition.NAME, new DropUnlitCondition.Serializer());

	public static LootItemConditionType register(String resourceName, Serializer<? extends LootItemCondition> lootSerializer) {
		return Registry.register(Registry.LOOT_CONDITION_TYPE, new ResourceLocation(RealisticTorches.MODID, resourceName), new LootItemConditionType(lootSerializer));
	}

}
