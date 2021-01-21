package com.hidoni.additionalenderitems.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class EnchantmentConfig
{
    public static ForgeConfigSpec.BooleanValue warpResistanceEnabled;
    public static ForgeConfigSpec.BooleanValue warpResistanceWithFeatherFalling;

    public static void init(ForgeConfigSpec.Builder commonBuilder)
    {
        warpResistanceEnabled = commonBuilder
                .comment("Controls whether the warp resistance enchantment can be acquired at the enchanting table")
                .define("enchantments.warp_resistance_enabled", true);
        warpResistanceWithFeatherFalling = commonBuilder
                .comment("Controls whether the warp resistance enchantment can be placed on an item with Feather Falling (and vice versa)")
                .define("enchantments.warp_resistance_with_feather_falling", false);
    }
}
