package com.hidoni.additionalenderitems.setup;

import com.hidoni.additionalenderitems.entities.EnderTorchEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;

public class ModEntities
{
    public static final RegistryObject<EntityType<EnderTorchEntity>> ENDER_TORCH = Registration.ENTITIES.register("ender_torch_entity", () -> EntityType.Builder.<EnderTorchEntity>create(EnderTorchEntity::new, EntityClassification.MISC).setCustomClientFactory(EnderTorchEntity::new).size(0.25F, 0.25F).trackingRange(4).func_233608_b_(4).build("ender_torch_entity"));

    public static void register()
    {
    }
}
