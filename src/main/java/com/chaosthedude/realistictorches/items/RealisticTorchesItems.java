package com.chaosthedude.realistictorches.items;

import com.chaosthedude.realistictorches.RealisticTorches;
import com.chaosthedude.realistictorches.config.ConfigHandler;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(RealisticTorches.MODID)
public class RealisticTorchesItems {

	@ObjectHolder(LitTorchItem.NAME)
	public static final LitTorchItem LIT_TORCH = null;

	@ObjectHolder(UnlitTorchItem.NAME)
	public static final UnlitTorchItem UNLIT_TORCH = null;

	@ObjectHolder(MatchboxItem.NAME)
	public static final MatchboxItem MATCHBOX = null;

	@ObjectHolder("glowstone_paste")
	public static final Item GLOWSTONE_PASTE = null;

	@ObjectHolder("glowstone_crystal")
	public static final Item GLOWSTONE_CRYSTAL = null;

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(
				new UnlitTorchItem(new Item.Properties()).setRegistryName(new ResourceLocation(RealisticTorches.MODID, UnlitTorchItem.NAME)),
				new LitTorchItem(new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)).setRegistryName(new ResourceLocation(RealisticTorches.MODID, LitTorchItem.NAME)),
				new MatchboxItem(ConfigHandler.matchboxDurability.get()).setRegistryName(new ResourceLocation(RealisticTorches.MODID, MatchboxItem.NAME)),
				new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)).setRegistryName(new ResourceLocation(RealisticTorches.MODID, "glowstone_paste")),
				new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)).setRegistryName(new ResourceLocation(RealisticTorches.MODID, "glowstone_crystal"))
		);
	}

}