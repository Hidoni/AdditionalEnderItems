package com.hidoni.additionalenderitems.setup;

import com.hidoni.additionalenderitems.AdditionalEnderItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

public class ModTags
{
    public static final class Blocks
    {
        private static ITag.INamedTag<Block> forge(String path)
        {
            return BlockTags.makeWrapperTag(new ResourceLocation("forge", path).toString());
        }

        private static ITag.INamedTag<Block> mod(String path)
        {
            return BlockTags.makeWrapperTag(new ResourceLocation(AdditionalEnderItems.MOD_ID, path).toString());
        }
    }

    public static final class Items
    {
        private static ITag.INamedTag<Item> forge(String path)
        {
            return ItemTags.makeWrapperTag(new ResourceLocation("forge", path).toString());
        }

        private static ITag.INamedTag<Item> mod(String path)
        {
            return ItemTags.makeWrapperTag(new ResourceLocation(AdditionalEnderItems.MOD_ID, path).toString());
        }
    }
}
