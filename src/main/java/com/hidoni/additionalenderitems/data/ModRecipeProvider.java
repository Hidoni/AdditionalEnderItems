package com.hidoni.additionalenderitems.data;

import com.hidoni.additionalenderitems.setup.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Items;

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
