package com.chaosthedude.realistictorches.conditions;

import com.chaosthedude.realistictorches.config.ConfigHandler;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class DropUnlitCondition implements LootItemCondition {
	
	public static final String NAME = "drop_unlit";

	private static final DropUnlitCondition INSTANCE = new DropUnlitCondition();

	private DropUnlitCondition() {
	}

	@Override
	public LootItemConditionType getType() {
		return RealisticTorchesConditions.DROP_UNLIT_TYPE;
	}

	@Override
	public boolean test(LootContext context) {
		return ConfigHandler.vanillaTorchesDropUnlit.get();
	}

	public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<DropUnlitCondition> {
		@Override
		public void serialize(JsonObject object, DropUnlitCondition condition, JsonSerializationContext context) {
		}

		@Override
		public DropUnlitCondition deserialize(JsonObject object, JsonDeserializationContext context) {
			return DropUnlitCondition.INSTANCE;
		}
	}

}
