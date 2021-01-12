package com.hidoni.additionalenderitems.setup;

import com.hidoni.additionalenderitems.enchantments.WarpResistanceEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.fml.RegistryObject;

public class ModEnchantments
{
    public static final RegistryObject<Enchantment> WARP_RESISTANCE = Registration.ENCHANTMENTS.register("warp_resistance", () -> new WarpResistanceEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentType.ARMOR_FEET, EquipmentSlotType.FEET));

    static void register()
    {
    }
}
