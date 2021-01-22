package com.hidoni.additionalenderitems.crafting;

import com.google.common.collect.Lists;
import com.hidoni.additionalenderitems.config.Config;
import com.hidoni.additionalenderitems.config.ItemConfig;
import com.hidoni.additionalenderitems.items.PearlStackItem;
import com.hidoni.additionalenderitems.setup.ModItems;
import com.hidoni.additionalenderitems.setup.ModRecipes;
import javafx.util.Pair;
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
        boolean allPearlStacksFlag = list.size() == 2 && list.get(0).getItem() == list.get(1).getItem() && list.get(0).getItem() == ModItems.PEARL_STACK.get();
        return (pearlStackFound && pearlFound) || allPearlStacksFlag;
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory inv)
    {
        Pair<Boolean, List<ItemStack>> validAndList = getValidAndList(inv);
        if (!validAndList.getKey())
        {
            return ItemStack.EMPTY;
        }
        List<ItemStack> itemList = validAndList.getValue();
        ItemStack primary = null;
        for (ItemStack item : itemList)
        {
            if (item.getItem() == ModItems.PEARL_STACK.get())
            {
                primary = item;
                break;
            }
        }
        itemList.remove(primary);
        if (!primary.hasTag())
        {
            primary.setTag(PearlStackItem.createNBT());
        }
        ItemStack outItem = new ItemStack(ModItems.PEARL_STACK.get(), 1);
        outItem.setTag(PearlStackItem.createNBT());
        outItem.getTag().putInt("pearls", primary.getTag().getInt("pearls"));
        primary.getTag().putBoolean("shouldReturn", false);
        for (ItemStack item : itemList)
        {
            if (item.getItem() == ModItems.PEARL_STACK.get())
            {
                int currentPearls = outItem.getTag().getInt("pearls");
                int maxPearlsToTake = ItemConfig.maxPearlsInStackItem.get() - currentPearls;
                int otherItemPearls = item.getTag().getInt("pearls");
                if (otherItemPearls >= maxPearlsToTake)
                {
                    item.getTag().putInt("pearls", otherItemPearls - maxPearlsToTake);
                    outItem.getTag().putInt("newPearls", currentPearls + maxPearlsToTake);
                }
                else
                {
                    item.getTag().putInt("newPearls", 0);
                    outItem.getTag().putInt("pearls", currentPearls + otherItemPearls);
                }
            }
            else
            {
                int currentPearls = outItem.getTag().getInt("pearls");
                if (currentPearls != ItemConfig.maxPearlsInStackItem.get())
                {
                    outItem.getTag().putInt("pearls", currentPearls + 1);
                }
            }
        }
        return outItem;
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

    public Pair<Boolean, List<ItemStack>> getValidAndList(CraftingInventory inv)
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
                    return new Pair(false, list);
                }
            }
        }
        boolean allPearlStacksFlag = list.size() == 2 && list.get(0).getItem() == list.get(1).getItem() && list.get(0).getItem() == ModItems.PEARL_STACK.get();
        return new Pair((pearlStackFound && pearlFound) || allPearlStacksFlag, list);
    }
}
