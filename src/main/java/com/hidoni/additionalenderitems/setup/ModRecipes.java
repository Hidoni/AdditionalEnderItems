package com.hidoni.additionalenderitems.setup;

import com.hidoni.additionalenderitems.crafting.ElytraDyeRecipe;
import com.hidoni.additionalenderitems.crafting.RefillPearlStackRecipe;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraftforge.fml.RegistryObject;

public class ModRecipes
{
    public static final RegistryObject<SpecialRecipeSerializer<RefillPearlStackRecipe>> PEARL_STACK_RECIPE = Registration.RECIPE_SERIALIZERS.register("pearl_stack_recipe", () -> new SpecialRecipeSerializer<>(RefillPearlStackRecipe::new));
    public static final RegistryObject<SpecialRecipeSerializer<ElytraDyeRecipe>> ELYTRA_DYE_RECIPE = Registration.RECIPE_SERIALIZERS.register("elytra_dye_recipe", () -> new SpecialRecipeSerializer<>(ElytraDyeRecipe::new));

    static void register()
    {

    }
}
