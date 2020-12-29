package com.chaosthedude.realistictorches.items;

import com.chaosthedude.realistictorches.RealisticTorches;
import com.chaosthedude.realistictorches.config.ConfigHandler;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(RealisticTorches.MODID)
public class RealisticTorchesItems {

    private static final Item.Properties PROPERTIES = new Item.Properties().group(ItemGroup.DECORATIONS);

    @ObjectHolder(LitTorchItem.NAME)
    public static final LitTorchItem LIT_TORCH = null;
    
    @ObjectHolder(UnlitTorchItem.NAME)
    public static final UnlitTorchItem UNLIT_TORCH = null;
    
    @ObjectHolder(MatchboxItem.NAME)
    public static final Item MATCHBOX = null;
    
    @ObjectHolder("glowstone_paste")
    public static final Item GLOWSTONE_PASTE = null;
    
    @ObjectHolder("glowstone_crystal")
    public static final Item GLOWSTONE_CRYSTAL = null;

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> itemRegistry) {
        itemRegistry.getRegistry().registerAll(
        		new LitTorchItem(PROPERTIES).setRegistryName(new ResourceLocation(RealisticTorches.MODID, LitTorchItem.NAME)),
        		new UnlitTorchItem(PROPERTIES).setRegistryName(new ResourceLocation(RealisticTorches.MODID, UnlitTorchItem.NAME)),
                new MatchboxItem(ConfigHandler.matchboxDurability.get()).setRegistryName(new ResourceLocation(RealisticTorches.MODID, MatchboxItem.NAME)),
                new Item(new Item.Properties().group(ItemGroup.MATERIALS)).setRegistryName(new ResourceLocation(RealisticTorches.MODID, "glowstone_paste")),
                new Item(new Item.Properties().group(ItemGroup.MATERIALS)).setRegistryName(new ResourceLocation(RealisticTorches.MODID, "glowstone_crystal"))
        );
    }

}
