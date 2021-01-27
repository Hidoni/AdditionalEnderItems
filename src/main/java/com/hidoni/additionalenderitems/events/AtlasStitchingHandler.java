package com.hidoni.additionalenderitems.events;

import com.hidoni.additionalenderitems.AdditionalEnderItems;
import com.hidoni.additionalenderitems.items.CustomizableElytraItem;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AtlasStitchingHandler
{
    @SubscribeEvent
    public static void onAtlasStiching(TextureStitchEvent.Pre event)
    {
        ResourceLocation stitching = event.getMap().getTextureLocation();
        if (stitching.equals(AtlasTexture.LOCATION_BLOCKS_TEXTURE))
        {
            boolean succeeded = event.addSprite(new ResourceLocation(AdditionalEnderItems.MOD_ID, "entity/ender_torch"));
            if (!succeeded)
            {
                AdditionalEnderItems.LOGGER.error("Failed to add ender_torch to texture atlas!");
            }

            for (BannerPattern bannerpattern : BannerPattern.values())
            {
                ResourceLocation textureLocation = CustomizableElytraItem.getTextureLocation(bannerpattern);
                succeeded = event.addSprite(textureLocation);
                if (!succeeded)
                {
                    AdditionalEnderItems.LOGGER.error("Failed to add " + textureLocation + " to texture atlas!");
                }
                else
                {
                    AdditionalEnderItems.LOGGER.debug("Added " + textureLocation + " to texture atlas.");
                }
            }

        }
    }
}
