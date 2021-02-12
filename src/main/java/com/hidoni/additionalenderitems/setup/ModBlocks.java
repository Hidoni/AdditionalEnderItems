package com.hidoni.additionalenderitems.setup;

import com.hidoni.additionalenderitems.blocks.DisenchantingBlock;
import com.hidoni.additionalenderitems.blocks.EnderJukeboxBlock;
import com.hidoni.additionalenderitems.blocks.EnderTorchBlock;
import com.hidoni.additionalenderitems.blocks.WarpPortalBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.particles.ParticleTypes;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks
{
    public static final RegistryObject<Block> ENDER_TORCH = registerNoItem("ender_torch", () -> new EnderTorchBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((state) ->
    {
        return 14;
    }).sound(SoundType.WOOD), ParticleTypes.PORTAL));
    public static final RegistryObject<Block> DISENCHANTING_TABLE = register("disenchanting_table", () -> new DisenchantingBlock(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(5.0F, 1200.0F).setRequiresTool()));
    public static final RegistryObject<Block> ENDER_JUKEBOX = register("ender_jukebox", () -> new EnderJukeboxBlock(AbstractBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0F, 6.0F)));
    public static final RegistryObject<Block> WARP_PORTAL = register("warp_portal", () -> new WarpPortalBlock(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(5.0F, 1200.0F).setRequiresTool().setLightLevel(WarpPortalBlock::getEmittedLightLevel)));

    static void register()
    {
    }

    private static <T extends Block> RegistryObject<T> registerNoItem(String name, Supplier<T> block)
    {
        return Registration.BLOCKS.register(name, block);
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block)
    {
        RegistryObject<T> returnValue = registerNoItem(name, block);
        Registration.ITEMS.register(name, () -> new BlockItem(returnValue.get(), new Item.Properties().group(ModItemGroup.ADDITIONAL_ENDER_ITEMS_GROUP)));
        return returnValue;
    }
}
