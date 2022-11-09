package com.chaosthedude.realistictorches.conditions;

import com.chaosthedude.realistictorches.config.ConfigHandler;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;

public class DropUnlitCondition implements ILootCondition {
	
	public static final String NAME = "drop_unlit";

	private static final DropUnlitCondition INSTANCE = new DropUnlitCondition();

	private DropUnlitCondition() {
	}

	@Override
	public LootConditionType getType() {
		return RealisticTorchesConditions.DROP_UNLIT_TYPE;
	}

	@Override
	public boolean test(LootContext context) {
		return ConfigHandler.vanillaTorchesDropUnlit.get();
	}

	public static class Serializer implements ILootSerializer<DropUnlitCondition> {
		@Override
		public void serialize(JsonObject object, DropUnlitCondition condition, JsonSerializationContext context) {
		}

		@Override
		public DropUnlitCondition deserialize(JsonObject object, JsonDeserializationContext context) {
			return DropUnlitCondition.INSTANCE;
		}
	}

}
