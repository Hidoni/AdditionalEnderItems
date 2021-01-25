package com.hidoni.additionalenderitems.events;

import com.hidoni.additionalenderitems.config.EntityConfig;
import com.hidoni.additionalenderitems.setup.ModEntities;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class BiomeLoadingHandler
{
    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onBiomeLoadingEvent(BiomeLoadingEvent event)
    {
        if (event.getCategory() == Biome.Category.THEEND) // Add Ender Phantoms to end biome spawns
        {
            if (EntityConfig.enderPhantomMobEnabled.get())
            {
                List<MobSpawnInfo.Spawners> spawns = event.getSpawns().getSpawner(EntityClassification.MONSTER);
                spawns.removeIf(e -> e.type == EntityType.ENDERMAN);
                spawns.add(new MobSpawnInfo.Spawners(EntityType.ENDERMAN, 50, 4, 4)); // Make endermen far more frequent to compensate for new addition
                spawns.add(new MobSpawnInfo.Spawners(ModEntities.ENDER_PHANTOM.get(), 5, 1, 2));
            }
        }
    }
}
