package com.chaosthedude.realistictorches.blocks;

import com.chaosthedude.realistictorches.RealisticTorches;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(RealisticTorches.MODID)
public class RealisticTorchesBlocks {

	@ObjectHolder(RealisticTorchBlock.NAME)
	public static final RealisticTorchBlock TORCH = null;

	@ObjectHolder(RealisticWallTorchBlock.NAME)
	public static final RealisticWallTorchBlock WALL_TORCH = null;

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(
				new RealisticTorchBlock().setRegistryName(new ResourceLocation(RealisticTorches.MODID, RealisticTorchBlock.NAME)),
				new RealisticWallTorchBlock().setRegistryName(new ResourceLocation(RealisticTorches.MODID, RealisticWallTorchBlock.NAME))
		);
	}

}
