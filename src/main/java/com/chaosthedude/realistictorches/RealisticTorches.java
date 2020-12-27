package com.chaosthedude.realistictorches;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.chaosthedude.realistictorches.blocks.RealisticTorchesBlocks;
import com.chaosthedude.realistictorches.config.ConfigHandler;
import com.chaosthedude.realistictorches.worldgen.TorchFeature;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.world.gen.GenerationStage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod(RealisticTorches.MODID)
public class RealisticTorches {

    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "realistictorches";

    public RealisticTorches() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHandler.COMMON_CONFIG);
        ConfigHandler.loadConfig(ConfigHandler.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("realistictorches-common.toml"));
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    private void clientSetup(final FMLClientSetupEvent event) {
        RenderTypeLookup.setRenderLayer(RealisticTorchesBlocks.TORCH, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(RealisticTorchesBlocks.WALL_TORCH, RenderType.getCutout());
    }


    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeEvents {

        @SubscribeEvent
        public static void setup(final BiomeLoadingEvent event) {
            event.getGeneration().withFeature(GenerationStage.Decoration.TOP_LAYER_MODIFICATION, TorchFeature.TORCH_CONFIGURED_FEATURE);
        }
    }
}
