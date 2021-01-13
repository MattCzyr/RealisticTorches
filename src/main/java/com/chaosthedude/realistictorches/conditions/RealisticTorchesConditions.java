package com.chaosthedude.realistictorches.conditions;

import com.chaosthedude.realistictorches.RealisticTorches;

import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class RealisticTorchesConditions {
	
	public static final LootConditionType DROP_UNLIT_TYPE = register("drop_unlit", new DropUnlitCondition.Serializer());
	
	public static void init() {
	}
	
	public static LootConditionType register(String resource_name, ILootSerializer<? extends ILootCondition> loot_serializer) {
        return Registry.register(Registry.LOOT_CONDITION_TYPE, new ResourceLocation(RealisticTorches.MODID + ":" + resource_name), new LootConditionType(loot_serializer));
    }

}
