package com.chaosthedude.realistictorches.util;

import com.chaosthedude.realistictorches.RealisticTorches;
import com.chaosthedude.realistictorches.RealisticTorchesBlocks;
import com.chaosthedude.realistictorches.RealisticTorchesItems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Util {

	public static void registerModels() {
		ItemModelMesher modelMesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();

		modelMesher.register(RealisticTorchesItems.matchbox, 0, new ModelResourceLocation(RealisticTorches.MODID + ":" + RealisticTorchesItems.matchbox.NAME, "inventory"));
		modelMesher.register(RealisticTorchesItems.glowstoneCrystal, 0, new ModelResourceLocation(RealisticTorches.MODID + ":GlowstoneCrystal", "inventory"));

		modelMesher.register(Item.getItemFromBlock(RealisticTorchesBlocks.torchLit), 0, new ModelResourceLocation(RealisticTorches.MODID + ":" + RealisticTorchesBlocks.torchLit.NAME, "inventory"));
		modelMesher.register(Item.getItemFromBlock(RealisticTorchesBlocks.torchSmoldering), 0, new ModelResourceLocation(RealisticTorches.MODID + ":" + RealisticTorchesBlocks.torchSmoldering.NAME, "inventory"));
		modelMesher.register(Item.getItemFromBlock(RealisticTorchesBlocks.torchUnlit), 0, new ModelResourceLocation(RealisticTorches.MODID + ":" + RealisticTorchesBlocks.torchUnlit.NAME, "inventory"));
	}

	public static EntityPlayer getClosestPlayer(World world, BlockPos pos, double distance) {
		double d0 = -1.0D;
		EntityPlayer player = null;

		for (int i = 0; i < world.playerEntities.size(); i++) {
			EntityPlayer player1 = (EntityPlayer) world.playerEntities.get(i);

			if (EntitySelectors.NOT_SPECTATING.apply(player1)) {
				double d1 = player1.getDistanceSq(pos.getX(), pos.getY(), pos.getZ());

				if ((distance < 0.0D || d1 < distance * distance) && (d0 == -1.0D || d1 < d0)) {
					d0 = d1;
					player = player1;
				}
			}
		}

		return player;
	}

}
