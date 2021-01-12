package com.hidoni.additionalenderitems.data;

import com.hidoni.additionalenderitems.AdditionalEnderItems;
import com.hidoni.additionalenderitems.setup.ModBlocks;
import com.hidoni.additionalenderitems.setup.ModTags;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.Tags;
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
