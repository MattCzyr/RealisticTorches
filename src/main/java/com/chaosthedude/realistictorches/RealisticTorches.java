package com.chaosthedude.realistictorches;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.chaosthedude.realistictorches.config.ConfigHandler;
import com.chaosthedude.realistictorches.registry.RealisticTorchesRegistry;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod(RealisticTorches.MODID)
public class RealisticTorches {

	public static final String MODID = "realistictorches";

	public static final Logger LOGGER = LogManager.getLogger();

	public RealisticTorches() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHandler.COMMON_CONFIG);
		ConfigHandler.loadConfig(ConfigHandler.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("realistictorches-common.toml"));

		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		eventBus.addListener(this::clientSetup);

		RealisticTorchesRegistry.FEATURE_REGISTRY.register(eventBus);
		RealisticTorchesRegistry.CONFIGURED_FEATURE_REGISTRY.register(eventBus);
		RealisticTorchesRegistry.PLACED_FEATURE_REGISTRY.register(eventBus);
		RealisticTorchesRegistry.BIOME_MODIFIER_SERIALIZERS.register(eventBus);
		RealisticTorchesRegistry.ITEM_REGISTRY.register(eventBus);
		RealisticTorchesRegistry.BLOCK_REGISTRY.register(eventBus);
		RealisticTorchesRegistry.LOOT_CONDITION_REGISTRY.register(eventBus);

		MinecraftForge.EVENT_BUS.register(this);
	}

	@OnlyIn(Dist.CLIENT)
	private void clientSetup(final FMLClientSetupEvent event) {
		ItemBlockRenderTypes.setRenderLayer(RealisticTorchesRegistry.TORCH_BLOCK.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(RealisticTorchesRegistry.TORCH_WALL_BLOCK.get(), RenderType.cutout());
	}

}
