package com.hidoni.additionalenderitems.data;

import com.hidoni.additionalenderitems.AdditionalEnderItems;
import com.hidoni.additionalenderitems.data.client.ModBlocksStateProvider;
import com.hidoni.additionalenderitems.data.client.ModItemModelProvider;
import com.hidoni.additionalenderitems.data.client.ModLanguageProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = AdditionalEnderItems.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators
{
    private DataGenerators()
    {
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event)
    {
        AdditionalEnderItems.LOGGER.info("Beginning Data Generation Registration!");
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();

        generator.addProvider(new ModBlocksStateProvider(generator, fileHelper));
        generator.addProvider(new ModItemModelProvider(generator, fileHelper));

        ModBlockTagsProvider blockTagsProvider = new ModBlockTagsProvider(generator, fileHelper);
        generator.addProvider((blockTagsProvider));
        generator.addProvider(new ModItemTagsProvider(generator, blockTagsProvider, fileHelper));

        generator.addProvider(new ModLanguageProvider(generator));

        generator.addProvider(new ModRecipeProvider(generator));
        generator.addProvider(new ModLootTableProvider(generator));
        AdditionalEnderItems.LOGGER.info("Finished Data Generation Registration!");
    }
}
