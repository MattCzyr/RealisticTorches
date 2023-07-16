package com.chaosthedude.realistictorches.registry;

import com.chaosthedude.realistictorches.RealisticTorches;
import com.chaosthedude.realistictorches.blocks.RealisticTorchBlock;
import com.chaosthedude.realistictorches.blocks.RealisticWallTorchBlock;
import com.chaosthedude.realistictorches.conditions.DropUnlitCondition;
import com.chaosthedude.realistictorches.config.ConfigHandler;
import com.chaosthedude.realistictorches.items.LitTorchItem;
import com.chaosthedude.realistictorches.items.MatchboxItem;
import com.chaosthedude.realistictorches.items.UnlitTorchItem;
import com.chaosthedude.realistictorches.worldgen.TorchBiomeModifier;
import com.chaosthedude.realistictorches.worldgen.TorchFeature;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RealisticTorchesRegistry {

	// TODO: reimplement features
	
	// Features
	//public static final DeferredRegister<Feature<?>> FEATURE_REGISTRY = DeferredRegister.create(ForgeRegistries.FEATURES, RealisticTorches.MODID);
	//public static final RegistryObject<TorchFeature> TORCH_FEATURE = FEATURE_REGISTRY.register(TorchFeature.NAME, () -> new TorchFeature(NoneFeatureConfiguration.CODEC.stable()));
	
	// Configured features
	//public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURE_REGISTRY = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, RealisticTorches.MODID);
	//public static final RegistryObject<ConfiguredFeature<NoneFeatureConfiguration, TorchFeature>> TORCH_CONFIGURED_FEATURE = CONFIGURED_FEATURE_REGISTRY.register(TorchFeature.NAME, () -> new ConfiguredFeature<>(TORCH_FEATURE.get(), NoneFeatureConfiguration.INSTANCE));

	// Placed features
	//public static final DeferredRegister<PlacedFeature> PLACED_FEATURE_REGISTRY = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, RealisticTorches.MODID);
	//public static final RegistryObject<PlacedFeature> TORCH_PLACED_FEATURE = PLACED_FEATURE_REGISTRY.register(TorchFeature.NAME, () -> new PlacedFeature(Holder.hackyErase(TORCH_CONFIGURED_FEATURE.getHolder().get()), new ArrayList<>()));

	// Biome modifiers
	public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, RealisticTorches.MODID);
	public static final RegistryObject<Codec<TorchBiomeModifier>> TORCH_BIOME_MODIFIER = BIOME_MODIFIER_SERIALIZERS.register(TorchFeature.NAME, () -> RecordCodecBuilder.create(builder -> builder.group(PlacedFeature.CODEC.fieldOf("feature").forGetter(TorchBiomeModifier::feature)).apply(builder, TorchBiomeModifier::new)));

	// Blocks
	public static final DeferredRegister<Block> BLOCK_REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, RealisticTorches.MODID);
	public static final RegistryObject<Block> TORCH_BLOCK = BLOCK_REGISTRY.register(RealisticTorchBlock.NAME, () -> new RealisticTorchBlock());
	public static final RegistryObject<Block> TORCH_WALL_BLOCK = BLOCK_REGISTRY.register(RealisticWallTorchBlock.NAME, () -> new RealisticWallTorchBlock());

	// Items
	public static final DeferredRegister<Item> ITEM_REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, RealisticTorches.MODID);
	public static final RegistryObject<Item> UNLIT_TORCH_ITEM = ITEM_REGISTRY.register(UnlitTorchItem.NAME, () -> new UnlitTorchItem(new Item.Properties(), Direction.DOWN));
	public static final RegistryObject<Item> LIT_TORCH_ITEM = ITEM_REGISTRY.register(LitTorchItem.NAME, () -> new LitTorchItem(new Item.Properties(), Direction.DOWN));
	public static final RegistryObject<Item> MATCHBOX_ITEM = ITEM_REGISTRY.register(MatchboxItem.NAME, () -> new MatchboxItem(ConfigHandler.matchboxDurability.get()));
	public static final RegistryObject<Item> GLOWSTONE_PASTE_ITEM = ITEM_REGISTRY.register("glowstone_paste", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> GLOWSTONE_CRYSTAL_ITEM = ITEM_REGISTRY.register("glowstone_crystal", () -> new Item(new Item.Properties()));

	// Loot conditions
	public static final DeferredRegister<LootItemConditionType> LOOT_CONDITION_REGISTRY = DeferredRegister.create(Registries.LOOT_CONDITION_TYPE, RealisticTorches.MODID);
	public static final RegistryObject<LootItemConditionType> DROP_UNLIT_CONDITION = LOOT_CONDITION_REGISTRY.register(DropUnlitCondition.NAME, () -> new LootItemConditionType(new DropUnlitCondition.Serializer()));

}
