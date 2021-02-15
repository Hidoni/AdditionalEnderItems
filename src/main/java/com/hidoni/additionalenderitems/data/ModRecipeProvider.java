package com.hidoni.additionalenderitems.data;

import com.hidoni.additionalenderitems.setup.ModBlocks;
import com.hidoni.additionalenderitems.setup.ModItems;
import com.hidoni.additionalenderitems.setup.ModRecipes;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.data.*;
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
        ShapedRecipeBuilder.shapedRecipe(ModItems.PEARL_STACK.get())
                .patternLine("Y Y")
                .patternLine("YXY")
                .patternLine("YYY")
                .key('X', Items.ENDER_EYE)
                .key('Y', Items.LEATHER)
                .addCriterion("has_item", hasItem(Items.ENDER_PEARL))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.WARP_PORTAL.get())
                .patternLine("XXX")
                .patternLine("YYY")
                .patternLine("YYY")
                .key('X', Items.ENDER_EYE)
                .key('Y', Items.END_STONE)
                .addCriterion("has_item", hasItem(Items.END_STONE))
                .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(ModItems.DRAGON_CHARGE.get())
                .addIngredient(Items.FIRE_CHARGE)
                .addIngredient(Items.DRAGON_BREATH)
                .addCriterion("has_item", hasItem(Items.DRAGON_BREATH))
                .build(consumer);

        CustomRecipeBuilder.customRecipe(ModRecipes.PEARL_STACK_RECIPE.get()).build(consumer, "pearl_stack_recipe");
    }
}
