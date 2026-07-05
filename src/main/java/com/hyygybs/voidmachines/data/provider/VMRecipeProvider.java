package com.hyygybs.voidmachines.data.provider;

import com.hyygybs.voidmachines.common.registration.VMBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import com.hyygybs.voidmachines.common.registration.VMItems;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

public class VMRecipeProvider extends RecipeProvider {
    public VMRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, VMItems.COLORFUL_VOID_MATTER.get())
                .define('R', VMItems.RED_VOID_MATTER.get())
                .define('G', VMItems.GREEN_VOID_MATTER.get())
                .define('V', VMItems.VIOLET_VOID_MATTER.get())
                .define('Y', VMItems.YELLOW_VOID_MATTER.get())
                .define('B', VMItems.BLUE_VOID_MATTER.get())
                .pattern(" R ")
                .pattern("GVY")
                .pattern(" B ")
                .unlockedBy("has_red_void_matter", has(VMItems.RED_VOID_MATTER.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, VMItems.SPEED_UPGRADE.get())
                .define('B', VMItems.BLACK_VOID_MATTER.get())
                .define('W', VMItems.WHITE_VOID_MATTER.get())
                .define('C', VMItems.COLORFUL_VOID_MATTER.get())
                .pattern("BBW")
                .pattern("BCW")
                .pattern("BWW")
                .unlockedBy("has_colorful_void_matter", has(VMItems.COLORFUL_VOID_MATTER.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, VMBlocks.VOID_ENERGY_GENERATOR.get())
                .define('C', Items.COAL_BLOCK)
                .define('V', VMItems.COLORFUL_VOID_MATTER.get())
                .pattern("CCC")
                .pattern("CVC")
                .pattern("CCC")
                .unlockedBy("has_colorful_void_matter", has(VMItems.COLORFUL_VOID_MATTER.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, VMBlocks.VOID_ORE_GENERATOR.get())
                .define('S', Items.STONE)
                .define('V', VMItems.COLORFUL_VOID_MATTER.get())
                .pattern("SSS")
                .pattern("SVS")
                .pattern("SSS")
                .unlockedBy("has_colorful_void_matter", has(VMItems.COLORFUL_VOID_MATTER.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, VMBlocks.VOID_NATURE_GENERATOR.get())
                .define('H', Items.HAY_BLOCK)
                .define('V', VMItems.COLORFUL_VOID_MATTER.get())
                .pattern("HHH")
                .pattern("HVH")
                .pattern("HHH")
                .unlockedBy("has_colorful_void_matter", has(VMItems.COLORFUL_VOID_MATTER.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, VMBlocks.VOID_MOB_GENERATOR.get())
                .define('W', Items.WHITE_WOOL)
                .define('V', VMItems.COLORFUL_VOID_MATTER.get())
                .pattern("WWW")
                .pattern("WVW")
                .pattern("WWW")
                .unlockedBy("has_colorful_void_matter", has(VMItems.COLORFUL_VOID_MATTER.get()))
                .save(recipeOutput);
    }
}
