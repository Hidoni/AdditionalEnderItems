package com.hidoni.additionalenderitems.data.client;

import com.hidoni.additionalenderitems.AdditionalEnderItems;
import com.hidoni.additionalenderitems.blocks.WarpPortalBlock;
import com.hidoni.additionalenderitems.setup.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.Direction;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
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

        getVariantBuilder(ModBlocks.WARP_PORTAL.get()).forAllStates((state) ->
        {
            int charge = state.get(WarpPortalBlock.CHARGES);
            //return ConfiguredModel.builder().modelFile(models().cubeBottomTop("warp_portal_" + charge, modLoc("block/warp_portal_side" + charge), modLoc("block/warp_portal_bottom"), modLoc("block/warp_portal_top_" + (charge == 0 ? "off" : "on")))).build();
            return ConfiguredModel.builder().modelFile(models().withExistingParent("warp_portal_" + charge, "block/block").texture("particle", modLoc("block/warp_portal_side" + charge)).texture("bottom", modLoc("block/warp_portal_bottom")).texture("side", modLoc("block/warp_portal_side" + charge)).texture("top", modLoc("block/warp_portal_top_" + (charge == 0 ? "off" : "on"))).element().from(0, 0, 0).to(16, 13, 16).allFaces(((direction, faceBuilder) ->
            {
                faceBuilder.texture(warpPortalDirectionToTexture(direction)).cullface(direction == Direction.UP ? null : direction).end();
            })).end()).build();
        });
    }

    private String warpPortalDirectionToTexture(Direction dir)
    {
        if (dir == Direction.DOWN)
        {
            return "#bottom";
        }
        else if (dir == Direction.UP)
        {
            return "#top";
        }
        return "#side";
    }

    private boolean isTopOrBottom(Direction dir)
    {
        return dir == Direction.UP || dir == Direction.DOWN;
    }
}