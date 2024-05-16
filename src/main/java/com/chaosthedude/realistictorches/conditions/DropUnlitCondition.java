package com.chaosthedude.realistictorches.conditions;

import com.chaosthedude.realistictorches.config.ConfigHandler;
import com.chaosthedude.realistictorches.registry.RealisticTorchesRegistry;
import com.mojang.serialization.MapCodec;

import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class DropUnlitCondition implements LootItemCondition {

	public static final String NAME = "drop_unlit";

	private static final DropUnlitCondition INSTANCE = new DropUnlitCondition();
	
	public static final MapCodec<DropUnlitCondition> CODEC = MapCodec.unit(INSTANCE);

	private DropUnlitCondition() {
	}

	@Override
	public LootItemConditionType getType() {
		return RealisticTorchesRegistry.DROP_UNLIT_CONDITION.get();
	}

	@Override
	public boolean test(LootContext context) {
		return ConfigHandler.vanillaTorchesDropUnlit.get();
	}

}
