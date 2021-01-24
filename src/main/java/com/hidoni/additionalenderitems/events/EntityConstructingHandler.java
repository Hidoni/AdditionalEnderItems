package com.hidoni.additionalenderitems.events;

import com.hidoni.additionalenderitems.renderers.layers.DyeableElytraLayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;

public class EntityConstructingHandler
{
    private static boolean initializedYet = false;
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void addDyeableElytraLayerToPlayer(EntityEvent.EntityConstructing event)
    {
        if (!initializedYet)
        {
            Entity entityIn = event.getEntity();
            if (entityIn instanceof AbstractClientPlayerEntity)
            {
                initializedYet = true;
            }
        }
    }
}
