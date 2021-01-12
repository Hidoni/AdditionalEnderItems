package com.hidoni.additionalenderitems.setup;

import com.hidoni.additionalenderitems.items.EnderTorchItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

public class ModItems
{
    public static final RegistryObject<Item> ENDER_TORCH = Registration.ITEMS.register("ender_torch", () -> new EnderTorchItem(ModBlocks.ENDER_TORCH.get(), new Item.Properties().group(ModItemGroup.ADDITIONAL_ENDER_ITEMS_GROUP)));

    static void register()
    {
    }
}
