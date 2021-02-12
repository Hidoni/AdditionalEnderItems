package com.hidoni.additionalenderitems.events;

import com.hidoni.additionalenderitems.entities.EnderTorchEntity;
import com.hidoni.additionalenderitems.gui.DisenchantingBlockContainerScreen;
import com.hidoni.additionalenderitems.renderers.EnderPhantomEntityRenderer;
import com.hidoni.additionalenderitems.renderers.EnderTorchTileEntityRenderer;
import com.hidoni.additionalenderitems.renderers.WarpPortalTileEntityRenderer;
import com.hidoni.additionalenderitems.setup.*;
import com.hidoni.additionalenderitems.util.MusicDiscPlayingUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import javax.annotation.Nullable;

public class ClientEventHandler
{
    public static void handleClientLoading(final FMLClientSetupEvent event)
    {
        Minecraft minecraft = Minecraft.getInstance();
        MusicDiscPlayingUtil.setMinecraft(minecraft);
        RenderTypeLookup.setRenderLayer(ModBlocks.ENDER_TORCH.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.WARP_PORTAL.get(), RenderType.getCutoutMipped());
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.ENDER_TORCH.get(), renderManager -> new SpriteRenderer<EnderTorchEntity>(renderManager, minecraft.getItemRenderer(), 1.0F, true));
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.ENDER_PHANTOM.get(), EnderPhantomEntityRenderer::new);
        ClientRegistry.bindTileEntityRenderer(ModTileEntities.ENDER_TORCH.get(), EnderTorchTileEntityRenderer::new);
        ClientRegistry.bindTileEntityRenderer(ModTileEntities.WARP_PORTAL.get(), WarpPortalTileEntityRenderer::new);
        ScreenManager.registerFactory(ModContainers.DISENCHANTING_TABLE.get(), DisenchantingBlockContainerScreen::new);
    }
}
