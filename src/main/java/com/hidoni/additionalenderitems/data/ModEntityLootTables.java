package com.hidoni.additionalenderitems.data;

import com.hidoni.additionalenderitems.AdditionalEnderItems;
import com.hidoni.additionalenderitems.setup.ModEntities;
import net.minecraft.data.loot.EntityLootTables;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.KilledByPlayer;
import net.minecraft.loot.conditions.RandomChanceWithLooting;
import net.minecraft.loot.functions.LootingEnchantBonus;
import net.minecraft.loot.functions.SetCount;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.stream.Collectors;

public class ModEntityLootTables extends EntityLootTables
{
    @Override
    protected void addTables()
    {
        registerLootTable(ModEntities.ENDER_PHANTOM.get(), LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.PHANTOM_MEMBRANE).acceptFunction(SetCount.builder(RandomValueRange.of(0.0F, 2.0F))).acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(0.0F, 1.0F)))).acceptCondition(KilledByPlayer.builder())).addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.ENDER_PEARL)).acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(0.0F, 1.0F))).acceptCondition(KilledByPlayer.builder()).acceptCondition(RandomChanceWithLooting.builder(0.2F, 0.1F))));
    }

    @Override
    protected Iterable<EntityType<?>> getKnownEntities()
    {
        return ForgeRegistries.ENTITIES.getValues().stream()
                .filter(entity -> AdditionalEnderItems.MOD_ID.equals(entity.getRegistryName().getNamespace()))
                .collect(Collectors.toSet());
    }
}
