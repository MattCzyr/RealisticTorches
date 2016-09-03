package com.chaosthedude.realistictorches.util;

import javax.annotation.Nullable;

import com.chaosthedude.realistictorches.RealisticTorches;
import com.chaosthedude.realistictorches.RealisticTorchesBlocks;
import com.chaosthedude.realistictorches.RealisticTorchesItems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Util {

	@SideOnly(Side.CLIENT)
	public static void registerModels() {
		final ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();

		for (String name : RealisticTorchesItems.registry.keySet()) {
			registerModel(mesher, name, RealisticTorchesItems.registry.get(name));
		}

		for (String name : RealisticTorchesBlocks.registry.keySet()) {
			registerModel(mesher, name, Item.getItemFromBlock(RealisticTorchesBlocks.registry.get(name)));
		}
	}

	@SideOnly(Side.CLIENT)
	public static void registerModel(ItemModelMesher mesher, String name, Item item) {
		mesher.register(item, 0, new ModelResourceLocation(RealisticTorches.MODID + ":" + name, "inventory"));
	}

	@Nullable
	public static EntityPlayer getClosestPlayer(World world, BlockPos pos, double distance) {
		double minDistance = -1.0D;
		EntityPlayer closestPlayer = null;

		for (EntityPlayer player : world.playerEntities) {
			if (EntitySelectors.NOT_SPECTATING.apply(player)) {
				final double playerDistance = player.getDistanceSq(pos.getX(), pos.getY(), pos.getZ());

				if ((distance < 0.0D || playerDistance < distance * distance) && (minDistance == -1.0D || playerDistance < minDistance)) {
					minDistance = playerDistance;
					closestPlayer = player;
				}
			}
		}

		return closestPlayer;
	}

}
