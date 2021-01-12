package com.hidoni.additionalenderitems.data;

import com.hidoni.additionalenderitems.AdditionalEnderItems;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemTagsProvider extends ItemTagsProvider
{
    public ModItemTagsProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, ExistingFileHelper existingFileHelper)
    {
        super(dataGenerator, blockTagProvider, AdditionalEnderItems.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerTags()
    {

    }
}
