package com.hidoni.additionalenderitems.events;


import com.hidoni.additionalenderitems.entities.EnderPhantomEntity;
import com.hidoni.additionalenderitems.items.ModdedSpawnEggItem;
import com.hidoni.additionalenderitems.setup.ModEntities;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

// You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
// Event bus for receiving Registry Events)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryEventsHandler
{
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPostRegisterEntities(final RegistryEvent.Register<EntityType<?>> event)
    {
        ModdedSpawnEggItem.initUnaddedEggs();
        EntitySpawnPlacementRegistry.register(ModEntities.ENDER_PHANTOM.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EnderPhantomEntity::canSpawnOn);
    }
}