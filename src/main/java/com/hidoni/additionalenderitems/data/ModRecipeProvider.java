package com.hidoni.additionalenderitems.data;

import com.hidoni.additionalenderitems.AdditionalEnderItems;
import com.hidoni.additionalenderitems.setup.ModBlocks;
import com.hidoni.additionalenderitems.setup.ModItems;
import com.hidoni.additionalenderitems.setup.ModTags;
import net.minecraft.data.*;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider
{

    public ModRecipeProvider(DataGenerator generatorIn)
    {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer)
    {
        ShapedRecipeBuilder.shapedRecipe(ModItems.ENDER_TORCH.get())
                .patternLine("X")
                .patternLine("Y")
                .key('X', Items.ENDER_EYE)
                .key('Y', Items.STICK)
                .setGroup("AdditionalEnderItems").addCriterion("has_item", hasItem(Items.ENDER_EYE))
                .build(consumer);
    }
}
