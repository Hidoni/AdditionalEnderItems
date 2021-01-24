package com.hidoni.additionalenderitems.data.client;

import com.hidoni.additionalenderitems.AdditionalEnderItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider
{
    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper)
    {
        super(generator, AdditionalEnderItems.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels()
    {
        withExistingParent("disenchanting_table", modLoc("block/disenchanting_table"));
        withExistingParent("ender_phantom_spawn_egg", mcLoc("item/template_spawn_egg"));
        withExistingParent("ender_jukebox", modLoc("block/ender_jukebox"));
        withExistingParent("dyeable_elytra", mcLoc("item/elytra")).override().predicate(new ResourceLocation("broken_elytra"), 1).model(new ModelFile.ExistingModelFile(mcLoc("item/broken_elytra"), existingFileHelper)).end();

        ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));

        builder(itemGenerated, "ender_torch");
        builder(itemGenerated, "pearl_stack");
    }

    private ItemModelBuilder builder(ModelFile itemGenerated, String name)
    {
        return getBuilder(name).parent(itemGenerated).texture("layer0", "item/" + name);
    }

}
