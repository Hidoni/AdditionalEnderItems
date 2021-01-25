package com.hidoni.additionalenderitems.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class EntityConfig
{
    public static ForgeConfigSpec.BooleanValue enderPhantomMobEnabled;
    public static ForgeConfigSpec.DoubleValue enderPhantomBaseHealth;
    public static ForgeConfigSpec.DoubleValue enderPhantomBaseDamage;

    public static void init(ForgeConfigSpec.Builder commonBuilder)
    {
        enderPhantomMobEnabled = commonBuilder
                .comment("Controls whether Ender Phantoms can spawn (requires reload)")
                .define("entitises.enable_ender_phantom", true);
        enderPhantomBaseHealth = commonBuilder
                .comment("Sets the base health of the Ender Phantom")
                .defineInRange("entities.ender_phantom_base_health", 40.0D, 1.0D, Double.MAX_VALUE);
        enderPhantomBaseDamage = commonBuilder
                .comment("Sets the base damage of the Ender Phantom (The normal difficulty damage)")
                .defineInRange("entities.ender_phantom_base_damage", 8.0D, 0.0D, Double.MAX_VALUE);
    }
}
