package com.hidoni.additionalenderitems.setup;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import java.util.function.Supplier;

public class ModItemGroup extends ItemGroup
{
    public static final ModItemGroup ADDITIONAL_ENDER_ITEMS_GROUP = new ModItemGroup("additionalenderitemsitemgroup", () -> new ItemStack(ModItems.ENDER_TORCH.get()));
    private final Supplier<ItemStack> displayStack;

    public ModItemGroup(String label, Supplier<ItemStack> displayStack)
    {
        super(label);
        this.displayStack = displayStack;
    }

    @Override
    public ItemStack createIcon()
    {
        return displayStack.get();
    }
}
