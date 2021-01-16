package com.hidoni.additionalenderitems.events;

import com.hidoni.additionalenderitems.entities.EnderTorchEntity;
import com.hidoni.additionalenderitems.gui.DisenchantingBlockContainerScreen;
import com.hidoni.additionalenderitems.renderers.EnderTorchTileEntityRenderer;
import com.hidoni.additionalenderitems.setup.ModBlocks;
import com.hidoni.additionalenderitems.setup.ModContainers;
import com.hidoni.additionalenderitems.setup.ModEntities;
import com.hidoni.additionalenderitems.setup.ModTileEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientEventHandler
{
    public static void handleClientLoading(final FMLClientSetupEvent event)
    {
        RenderTypeLookup.setRenderLayer(ModBlocks.ENDER_TORCH.get(), RenderType.getCutout());
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.ENDER_TORCH.get(), renderManager -> new SpriteRenderer<EnderTorchEntity>(renderManager, Minecraft.getInstance().getItemRenderer(), 1.0F, true));
        ClientRegistry.bindTileEntityRenderer(ModTileEntities.ENDER_TORCH.get(), EnderTorchTileEntityRenderer::new);
        ScreenManager.registerFactory(ModContainers.DISENCHANTING_TABLE.get(), DisenchantingBlockContainerScreen::new);
    }
}
