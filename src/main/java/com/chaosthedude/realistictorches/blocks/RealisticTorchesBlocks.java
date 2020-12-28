package com.chaosthedude.realistictorches.blocks;

import com.chaosthedude.realistictorches.RealisticTorches;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(RealisticTorches.MODID)
public class RealisticTorchesBlocks {

	@ObjectHolder(UnlitTorchBlock.NAME)
    public static final UnlitTorchBlock UNLIT_TORCH = null;
    
    @ObjectHolder(SmolderingTorchBlock.NAME)
    public static final SmolderingTorchBlock SMOLDERING_TORCH = null;
    
    @ObjectHolder(LitTorchBlock.NAME)
    public static final LitTorchBlock LIT_TORCH = null;
    
    @ObjectHolder(UnlitWallTorchBlock.NAME)
    public static final UnlitWallTorchBlock UNLIT_WALL_TORCH = null;
    
    @ObjectHolder(SmolderingWallTorchBlock.NAME)
    public static final SmolderingWallTorchBlock SMOLDERING_WALL_TORCH = null;
    
    @ObjectHolder(LitWallTorchBlock.NAME)
    public static final LitWallTorchBlock LIT_WALL_TORCH = null;

    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> blockRegistry) {
        blockRegistry.getRegistry().registerAll(
        		new UnlitTorchBlock().setRegistryName(new ResourceLocation(RealisticTorches.MODID, UnlitTorchBlock.NAME)),
        		new SmolderingTorchBlock().setRegistryName(new ResourceLocation(RealisticTorches.MODID, SmolderingTorchBlock.NAME)),
        		new LitTorchBlock().setRegistryName(new ResourceLocation(RealisticTorches.MODID, LitTorchBlock.NAME)),
        		new UnlitWallTorchBlock().setRegistryName(new ResourceLocation(RealisticTorches.MODID, UnlitWallTorchBlock.NAME)),
        		new SmolderingWallTorchBlock().setRegistryName(new ResourceLocation(RealisticTorches.MODID, SmolderingWallTorchBlock.NAME)),
        		new LitWallTorchBlock().setRegistryName(new ResourceLocation(RealisticTorches.MODID, LitWallTorchBlock.NAME))
        );
    }

}
