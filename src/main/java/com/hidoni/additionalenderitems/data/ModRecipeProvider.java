package com.hidoni.additionalenderitems.data;

import com.hidoni.additionalenderitems.setup.ModBlocks;
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
                .addCriterion("has_item", hasItem(Items.ENDER_EYE))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.DISENCHANTING_TABLE.get())
                .patternLine("XZX")
                .patternLine("XXX")
                .patternLine("YYY")
                .key('X', Items.PHANTOM_MEMBRANE)
                .key('Y', Items.END_STONE)
                .key('Z', Items.LAPIS_LAZULI)
                .addCriterion("has_item", hasItem(Items.END_STONE))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.ENDER_JUKEBOX.get())
                .patternLine(" X ")
                .patternLine("XYX")
                .patternLine(" X ")
                .key('X', Items.ENDER_PEARL)
                .key('Y', Items.JUKEBOX)
                .addCriterion("has_item", hasItem(Items.ENDER_PEARL))
                .build(consumer);
    }
}
