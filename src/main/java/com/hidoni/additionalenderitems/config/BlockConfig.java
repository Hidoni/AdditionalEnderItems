package com.hidoni.additionalenderitems.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class BlockConfig
{
    public static ForgeConfigSpec.BooleanValue disenchantingCursedItemPenalty;
    public static ForgeConfigSpec.BooleanValue disenchantingTreasureItemPenalty;

    public static void init(ForgeConfigSpec.Builder commonBuilder)
    {
        disenchantingCursedItemPenalty = commonBuilder
                .comment("Controls whether cursed enchantments on items will require additional experience to remove")
                .define("blocks.disenchanting_cursed_item_penalty", true);
        disenchantingTreasureItemPenalty = commonBuilder
                .comment("Controls whether treasure enchantments on items will require additional experience to remove")
                .define("blocks.disenchanting_treasure_item_penalty", true);
    }
}
