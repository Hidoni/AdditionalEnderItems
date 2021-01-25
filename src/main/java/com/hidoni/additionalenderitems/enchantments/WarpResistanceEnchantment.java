package com.hidoni.additionalenderitems.enchantments;

import com.hidoni.additionalenderitems.config.EnchantmentConfig;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public class WarpResistanceEnchantment extends Enchantment
{
    public WarpResistanceEnchantment(Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType... slots)
    {
        super(rarityIn, EnchantmentType.ARMOR_FEET, slots);
    }

    @Override
    public boolean canApply(ItemStack stack)
    {
        return super.canApply(stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack)
    {
        return EnchantmentConfig.warpResistanceEnabled.get() && super.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean canApplyTogether(Enchantment ench)
    {
        if (!EnchantmentConfig.warpResistanceWithFeatherFalling.get())
        {
            if (ench instanceof ProtectionEnchantment)
            {
                if (((ProtectionEnchantment) ench).protectionType == ProtectionEnchantment.Type.FALL)
                {
                    return false;
                }
            }
        }
        return super.canApplyTogether(ench);
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel)
    {
        return 29;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel)
    {
        return this.getMinEnchantability(enchantmentLevel) + 8;
    }
}
