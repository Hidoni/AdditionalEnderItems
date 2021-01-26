package com.hidoni.additionalenderitems.events;

import com.hidoni.additionalenderitems.entities.EnderTorchEntity;
import com.hidoni.additionalenderitems.gui.DisenchantingBlockContainerScreen;
import com.hidoni.additionalenderitems.items.CustomizableElytraItem;
import com.hidoni.additionalenderitems.renderers.EnderPhantomEntityRenderer;
import com.hidoni.additionalenderitems.renderers.EnderTorchTileEntityRenderer;
import com.hidoni.additionalenderitems.renderers.layers.DyeableElytraLayer;
import com.hidoni.additionalenderitems.setup.*;
import com.hidoni.additionalenderitems.util.MusicDiscPlayingUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
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
        MusicDiscPlayingUtil.setMinecraft(Minecraft.getInstance());
        RenderTypeLookup.setRenderLayer(ModBlocks.ENDER_TORCH.get(), RenderType.getCutout());
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.ENDER_TORCH.get(), renderManager -> new SpriteRenderer<EnderTorchEntity>(renderManager, Minecraft.getInstance().getItemRenderer(), 1.0F, true));
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.ENDER_PHANTOM.get(), EnderPhantomEntityRenderer::new);
        ClientRegistry.bindTileEntityRenderer(ModTileEntities.ENDER_TORCH.get(), EnderTorchTileEntityRenderer::new);
        ScreenManager.registerFactory(ModContainers.DISENCHANTING_TABLE.get(), DisenchantingBlockContainerScreen::new);

        PlayerRenderer playerRenderer = Minecraft.getInstance().getRenderManager().getSkinMap().get("slim");
        playerRenderer.addLayer(new DyeableElytraLayer<>(playerRenderer));
        playerRenderer = Minecraft.getInstance().getRenderManager().getSkinMap().get("default");
        playerRenderer.addLayer(new DyeableElytraLayer<>(playerRenderer));

        ItemModelsProperties.registerProperty(ModItems.CUSTOMIZABLE_ELYTRA.get(), new ResourceLocation("broken_elytra"), new IItemPropertyGetter()
        {
            @Override
            public float call(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity)
            {
                return CustomizableElytraItem.isUsable(stack) ? 0 : 1;
            }
        });
    }
}
