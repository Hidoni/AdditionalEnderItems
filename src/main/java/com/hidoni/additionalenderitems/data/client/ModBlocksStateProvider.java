package com.hidoni.additionalenderitems.data.client;

import com.hidoni.additionalenderitems.AdditionalEnderItems;
import com.hidoni.additionalenderitems.setup.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlocksStateProvider extends BlockStateProvider
{
    public ModBlocksStateProvider(DataGenerator generator, ExistingFileHelper existingFileHelper)
    {
        super(generator, AdditionalEnderItems.MOD_ID, existingFileHelper);
    }

    @Override
    public void registerStatesAndModels()
    {
        simpleBlock(ModBlocks.ENDER_TORCH.get(), models().withExistingParent("ender_torch", mcLoc("block/template_torch")).texture("torch", modLoc("block/ender_torch")));
        simpleBlock(ModBlocks.DISENCHANTING_TABLE.get(), models().cubeBottomTop("disenchanting_table", modLoc("block/disenchanting_table_side"), modLoc("block/disenchanting_table_bottom"), modLoc("block/disenchanting_table_top")));
        simpleBlock(ModBlocks.ENDER_JUKEBOX.get(), models().cubeTop("ender_jukebox", modLoc("block/ender_jukebox_side"), modLoc("block/ender_jukebox_top")));
    }
}