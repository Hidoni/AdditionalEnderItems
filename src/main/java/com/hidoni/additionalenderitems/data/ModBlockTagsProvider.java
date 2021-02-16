package com.hidoni.additionalenderitems.data;

import com.hidoni.additionalenderitems.AdditionalEnderItems;
import com.hidoni.additionalenderitems.setup.ModBlocks;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockTagsProvider extends BlockTagsProvider
{
    public ModBlockTagsProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper)
    {
        super(generatorIn, AdditionalEnderItems.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerTags()
    {
        getOrCreateBuilder(BlockTags.DRAGON_IMMUNE).add(ModBlocks.WARP_PORTAL.get());
    }
}
