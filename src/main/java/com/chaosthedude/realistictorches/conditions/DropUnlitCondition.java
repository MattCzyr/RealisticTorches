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

	private static final DropUnlitCondition INSTANCE = new DropUnlitCondition();

	private DropUnlitCondition() {
	}

	@Override
	public LootConditionType func_230419_b_() {
		return RealisticTorchesConditions.DROP_UNLIT_TYPE;
	}

	@Override
	public boolean test(LootContext context) {
		return ConfigHandler.vanillaTorchesDropUnlit.get();
	}

	public static class Serializer implements ILootSerializer<DropUnlitCondition> {
		@Override
		public void serialize(JsonObject p_230424_1_, DropUnlitCondition p_230424_2_,
				JsonSerializationContext p_230424_3_) {
		}

		@Override
		public DropUnlitCondition deserialize(JsonObject obj, JsonDeserializationContext context) {
			return DropUnlitCondition.INSTANCE;
		}
	}
}
