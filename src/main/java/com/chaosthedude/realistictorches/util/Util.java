package com.chaosthedude.realistictorches.util;

import java.util.List;

import com.chaosthedude.realistictorches.RealisticTorches;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Util {

	public static EntityPlayer getClosestPlayer(World world, BlockPos pos, double distance) {
		double d0 = -1.0D;
		EntityPlayer player = null;

		for (int i = 0; i < world.playerEntities.size(); ++i) {
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

	public static void removeRecipe(ItemStack s) {
		int recipeCount = 0;
		List<IRecipe> recipeList = CraftingManager.getInstance().getRecipeList();
		for (int i = 0; i < recipeList.size(); i++) {
			IRecipe currentRecipe = recipeList.get(i);
			ItemStack output = currentRecipe.getRecipeOutput();
			if (output != null && output.getItem() == s.getItem()) {
				recipeList.remove(i);
				recipeCount++;
			}
		}

		if (recipeCount == 1) {
			RealisticTorches.logger.info("Removed " + recipeCount + " torch recipe.");
		} else {
			RealisticTorches.logger.info("Removed " + recipeCount + " torch recipes.");
		}
	}

	public static ItemStack copyStack(ItemStack stack, int n) {
		return new ItemStack(stack.getItem(), n, stack.getItemDamage());
	}

}
