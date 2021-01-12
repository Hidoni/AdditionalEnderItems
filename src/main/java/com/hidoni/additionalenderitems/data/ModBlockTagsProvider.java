package com.hidoni.additionalenderitems.data;

import com.hidoni.additionalenderitems.AdditionalEnderItems;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
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

    }
}
