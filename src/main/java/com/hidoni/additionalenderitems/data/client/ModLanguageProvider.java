package com.hidoni.additionalenderitems.data.client;

import com.hidoni.additionalenderitems.AdditionalEnderItems;
import com.hidoni.additionalenderitems.blocks.WarpPortalBlock;
import com.hidoni.additionalenderitems.items.PearlStackItem;
import com.hidoni.additionalenderitems.setup.ModBlocks;
import com.hidoni.additionalenderitems.setup.ModEnchantments;
import com.hidoni.additionalenderitems.setup.ModEntities;
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
        add(ModItems.ENDER_PHANTOM_SPAWN_EGG.get(), "Ender Phantom Spawn Egg");
        add(ModItems.PEARL_STACK.get(), "Warped Pearl Bundle");
        add(ModItems.DRAGON_CHARGE.get(), "Dragon Charge");
        add(ModBlocks.DISENCHANTING_TABLE.get(), "Disenchantment Table");
        add(ModBlocks.ENDER_JUKEBOX.get(), "Ender Jukebox");
        add(ModBlocks.WARP_PORTAL.get(), "Warp Portal");

        add(ModEnchantments.WARP_RESISTANCE.get(), "Warp Resistance");

        add(ModEntities.ENDER_PHANTOM.get(), "Ender Phantom");

        add("itemGroup.additionalenderitemsitemgroup", "Additional Ender Items");
        add("container.disenchanting_table_title", "Disenchanting Table");
        add(PearlStackItem.PEARL_TOOLTIP, "pearls");
        add(WarpPortalBlock.LOCAL_WARP_PORTAL_NO_CHARGE_MESSAGE, "This warp portal has no charge.");
        add(WarpPortalBlock.END_WARP_PORTAL_NO_CHARGE_MESSAGE, "Your end warp portal has no charge.");
        add(WarpPortalBlock.END_WARP_PORTAL_MISSING, "You have no end warp portal, or it was obstructed.");
        add(WarpPortalBlock.END_WARP_LOCATION_SET, "Warp location set.");

        add("subtitles.entity.additionalenderitems.ender_torch_launch", "Ender Torch shoots");
        add("subtitles.entity.additionalenderitems.ender_torch_place", "Ender Torch sets in place");
        add("subtitles.block.additionalenderitems.warp_portal_charge", "Warp Portal is charged");
        add("subtitles.entity.additionalenderitems.dragon_charge_launch", "Dragon Charge shoots");
    }
}
