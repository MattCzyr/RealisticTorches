package com.chaosthedude.realistictorches.handler;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

// Per http://www.minecraftforge.net/forum/topic/59153-112-removing-vanilla-recipes/#comment-274722
// it is recommended to replace recipes with a "null recipe" rather than removing them.
public class NullReplacementRecipe implements IRecipe {
    private IRecipe replacedRecipe;

    public NullReplacementRecipe(IRecipe replacedRecipe) {
        this.replacedRecipe = replacedRecipe;
    }

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        return false;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) { return replacedRecipe.getCraftingResult(inv); }

    @Override
    public boolean canFit(int width, int height) {
        return replacedRecipe.canFit(width, height);
    }

    @Override
    public ItemStack getRecipeOutput() { return ItemStack.EMPTY; }

    @Override
    public IRecipe setRegistryName(ResourceLocation name) {
        return replacedRecipe.setRegistryName(name);
    }

    @Nullable
    @Override
    public ResourceLocation getRegistryName() {
        return replacedRecipe.getRegistryName();
    }

    @Override
    public Class<IRecipe> getRegistryType() {
        return replacedRecipe.getRegistryType();
    }
}
