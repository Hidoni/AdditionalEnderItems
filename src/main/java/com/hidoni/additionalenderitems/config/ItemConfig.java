package com.hidoni.additionalenderitems.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ItemConfig
{
    public static ForgeConfigSpec.IntValue maxPearlsInStackItem;
    public static ForgeConfigSpec.IntValue enderTorchMaxTravelDistance;

    public static void init(ForgeConfigSpec.Builder commonBuilder)
    {
        maxPearlsInStackItem = commonBuilder.
                comment("Controls how many ender pearls the pearl stack item can store")
                .defineInRange("items.max_pearls_in_stack_item", 256, 1, Integer.MAX_VALUE);
    }
}
