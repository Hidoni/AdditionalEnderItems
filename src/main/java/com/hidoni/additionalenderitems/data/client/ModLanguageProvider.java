package com.hidoni.additionalenderitems.data.client;

import com.hidoni.additionalenderitems.AdditionalEnderItems;
import com.hidoni.additionalenderitems.setup.ModBlocks;
import com.hidoni.additionalenderitems.setup.ModEnchantments;
import com.hidoni.additionalenderitems.setup.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider
{

    public ModLanguageProvider(DataGenerator gen)
    {
        super(gen, AdditionalEnderItems.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations()
    {
        add(ModItems.ENDER_TORCH.get(), "Ender Torch");
        add(ModBlocks.DISENCHANTING_TABLE.get(), "Disenchantment Table");

        add(ModEnchantments.WARP_RESISTANCE.get(), "Warp Resistance");

        add("itemGroup.additionalenderitemsitemgroup", "Additional Ender Items");
    }
}
