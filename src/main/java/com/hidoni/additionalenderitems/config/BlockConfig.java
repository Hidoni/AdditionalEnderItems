package com.hidoni.additionalenderitems.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class BlockConfig
{
    public static ForgeConfigSpec.BooleanValue disenchantingCursedItemPenalty;
    public static ForgeConfigSpec.BooleanValue disenchantingTreasureItemPenalty;
    public static ForgeConfigSpec.BooleanValue disenchantingCostsXP;
    public static ForgeConfigSpec.BooleanValue disenchantingPhantomMembraneUsesStaticCost;
    public static ForgeConfigSpec.IntValue disenchantingPhantomMembraneStaticCost;
    public static ForgeConfigSpec.IntValue warpPortalPearlFuelValue;
    public static ForgeConfigSpec.IntValue warpPortalEyeFuelValue;

    public static void init(ForgeConfigSpec.Builder commonBuilder)
    {
        disenchantingCostsXP = commonBuilder
                .comment("Controls whether disenchantments require the player to have experience levels")
                .define("blocks.disenchanting_experience_cost_enabled", true);
        disenchantingCursedItemPenalty = commonBuilder
                .comment("Controls whether cursed enchantments on items will require additional experience to remove")
                .define("blocks.disenchanting_cursed_item_penalty", true);
        disenchantingTreasureItemPenalty = commonBuilder
                .comment("Controls whether treasure enchantments on items will require additional experience to remove")
                .define("blocks.disenchanting_treasure_item_penalty", true);
        disenchantingPhantomMembraneUsesStaticCost = commonBuilder
                .comment("Decides whether disenchanting takes a set amount of phantom membrane, or costs 1 membrane per enchantment level")
                .define("blocks.disenchanting_phantom_mebrane_uses_static_cost", false);
        disenchantingPhantomMembraneStaticCost = commonBuilder
                .comment("Controls the amount of phantom membrane that will be required for the disenchantment if the above variable is set to TRUE")
                .defineInRange("blocks.disenchanting_phantom_membrane_static_cost", 3, 1, Integer.MAX_VALUE);
        warpPortalPearlFuelValue = commonBuilder.comment("Controls how much fuel an ender pearl provides in the Warp Portal")
                .defineInRange("blocks.warp_portal_pearl_fuel_value", 1, 1, Integer.MAX_VALUE);
        warpPortalEyeFuelValue = commonBuilder.comment("Controls how much fuel an eye of ender provides in the Warp Portal")
                .defineInRange("blocks.warp_portal_pearl_eye_value", 2, 1, Integer.MAX_VALUE);
    }
}
