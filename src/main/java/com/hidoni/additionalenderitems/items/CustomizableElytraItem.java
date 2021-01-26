package com.hidoni.additionalenderitems.items;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

import javax.annotation.Nullable;

public class CustomizableElytraItem extends ElytraItem implements IDyeableArmorItem
{
    public CustomizableElytraItem(Properties builder)
    {
        super(builder);
    }

    @Override
    public int getColor(ItemStack stack)
    {
        CompoundNBT compoundnbt = stack.getChildTag("display");
        return compoundnbt != null && compoundnbt.contains("color", 99) ? compoundnbt.getInt("color") : 16777215;
    }

    @Nullable
    @Override
    public EquipmentSlotType getEquipmentSlot(ItemStack stack)
    {
        return EquipmentSlotType.CHEST;
    }
}
