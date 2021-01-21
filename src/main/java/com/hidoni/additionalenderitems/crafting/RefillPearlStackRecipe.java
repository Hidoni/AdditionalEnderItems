package com.hidoni.additionalenderitems.crafting;

import com.google.common.collect.Lists;
import com.hidoni.additionalenderitems.setup.ModItems;
import com.hidoni.additionalenderitems.setup.ModRecipes;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.List;

public class RefillPearlStackRecipe extends SpecialRecipe
{

    public RefillPearlStackRecipe(ResourceLocation idIn)
    {
        super(idIn);
    }

    @Override
    public boolean matches(CraftingInventory inv, World worldIn)
    {
        List<ItemStack> list = Lists.newArrayList();
        boolean pearlStackFound = false;
        boolean pearlFound = false;
        for(int i = 0; i < inv.getSizeInventory(); ++i)
        {
            ItemStack itemstack = inv.getStackInSlot(i);
            if (!itemstack.isEmpty())
            {
                list.add(itemstack);
                if (itemstack.getItem() == ModItems.PEARL_STACK.get())
                {
                    pearlStackFound = true;
                }
                else if (itemstack.getItem() == Items.ENDER_PEARL)
                {
                    pearlFound = true;
                }
                else
                {
                    return false;
                }
            }
        }
        boolean allPearlStacksFlag = true;
        for (ItemStack item : list)
        {
            if (item.getItem() != ModItems.PEARL_STACK.get())
            {
                allPearlStacksFlag = false;
            }
        }
        return (pearlStackFound && pearlFound) || allPearlStacksFlag;
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory inv)
    {
        return null;
    }

    @Override
    public boolean canFit(int width, int height)
    {
        return width * height >= 2;
    }

    @Override
    public IRecipeSerializer<?> getSerializer()
    {
        return ModRecipes.PEARL_STACK_RECIPE.get();
    }
}
