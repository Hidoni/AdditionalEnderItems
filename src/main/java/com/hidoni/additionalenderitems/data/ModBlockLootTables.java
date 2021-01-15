package com.hidoni.additionalenderitems.data;

import com.hidoni.additionalenderitems.AdditionalEnderItems;
import com.hidoni.additionalenderitems.setup.ModBlocks;
import com.hidoni.additionalenderitems.setup.ModItems;
import net.minecraft.block.Block;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.stream.Collectors;

public class ModBlockLootTables extends BlockLootTables
{
    @Override
    protected void addTables()
    {
        registerDropping(ModBlocks.ENDER_TORCH.get(), ModItems.ENDER_TORCH.get());
        registerDropSelfLootTable(ModBlocks.DISENCHANTING_TABLE.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks()
    {
        return ForgeRegistries.BLOCKS.getValues().stream()
                .filter(block -> AdditionalEnderItems.MOD_ID.equals(block.getRegistryName().getNamespace()))
                .collect(Collectors.toSet());
    }
}
