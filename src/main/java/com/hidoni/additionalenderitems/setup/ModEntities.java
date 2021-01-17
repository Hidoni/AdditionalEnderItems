package com.hidoni.additionalenderitems.setup;

import com.hidoni.additionalenderitems.entities.EnderPhantomEntity;
import com.hidoni.additionalenderitems.entities.EnderTorchEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.monster.PhantomEntity;
import net.minecraftforge.fml.RegistryObject;

public class ModEntities
{
    public static final RegistryObject<EntityType<EnderTorchEntity>> ENDER_TORCH = Registration.ENTITIES.register("ender_torch_entity", () -> EntityType.Builder.<EnderTorchEntity>create(EnderTorchEntity::new, EntityClassification.MISC).setCustomClientFactory(EnderTorchEntity::new).size(0.25F, 0.25F).trackingRange(4).func_233608_b_(4).build("ender_torch_entity"));
    public static final RegistryObject<EntityType<EnderPhantomEntity>> ENDER_PHANTOM = Registration.ENTITIES.register("ender_phantom", () -> EntityType.Builder.<EnderPhantomEntity>create(EnderPhantomEntity::new, EntityClassification.MONSTER).setCustomClientFactory(EnderPhantomEntity::new).size(0.9F, 0.5F).trackingRange(8).build("ender_phantom_entity"));

    public static void register()
    {
    }
}
