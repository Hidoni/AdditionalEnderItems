package com.hidoni.additionalenderitems.renderers.layers;

import com.hidoni.additionalenderitems.items.DyeableElytraItem;
import com.hidoni.additionalenderitems.setup.ModItems;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.model.ElytraModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class DyeableElytraLayer<T extends LivingEntity, M extends EntityModel<T>> extends ElytraLayer<T, M>
{
    private final ElytraModel<T> modelElytra = new ElytraModel<>();
    public DyeableElytraLayer(IEntityRenderer rendererIn)
    {
        super(rendererIn);
    }


    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch)
    {
        ItemStack elytra = entitylivingbaseIn.getItemStackFromSlot(EquipmentSlotType.CHEST);
        List<Float> colors = getColors(elytra);
        if (colors != null)
        {
            ItemStack itemstack = entitylivingbaseIn.getItemStackFromSlot(EquipmentSlotType.CHEST);
            if (shouldRender(itemstack, entitylivingbaseIn))
            {
                ResourceLocation resourcelocation;
                if (entitylivingbaseIn instanceof AbstractClientPlayerEntity)
                {
                    AbstractClientPlayerEntity abstractclientplayerentity = (AbstractClientPlayerEntity) entitylivingbaseIn;
                    if (abstractclientplayerentity.isPlayerInfoSet() && abstractclientplayerentity.getLocationElytra() != null)
                    {
                        resourcelocation = abstractclientplayerentity.getLocationElytra();
                    }
                    else if (abstractclientplayerentity.hasPlayerInfo() && abstractclientplayerentity.getLocationCape() != null && abstractclientplayerentity.isWearing(PlayerModelPart.CAPE))
                    {
                        resourcelocation = abstractclientplayerentity.getLocationCape();
                    }
                    else
                    {
                        resourcelocation = getElytraTexture(itemstack, entitylivingbaseIn);
                    }
                }
                else
                {
                    resourcelocation = getElytraTexture(itemstack, entitylivingbaseIn);
                }

                matrixStackIn.push();
                matrixStackIn.translate(0.0D, 0.0D, 0.125D);
                this.getEntityModel().copyModelAttributesTo(this.modelElytra);
                this.modelElytra.setRotationAngles(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                IVertexBuilder ivertexbuilder = ItemRenderer.getArmorVertexBuilder(bufferIn, RenderType.getArmorCutoutNoCull(resourcelocation), false, itemstack.hasEffect());
                ivertexbuilder.color(colors.get(0), colors.get(1), colors.get(2), 1.0F);
                this.modelElytra.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
                matrixStackIn.pop();
            }
        }
    }

    @Override
    public boolean shouldRender(ItemStack stack, LivingEntity entity)
    {
        return stack.getItem() == ModItems.DYEABLE_ELYTRA.get();
    }

    public List<Float> getColors(ItemStack elytraIn)
    {
        ArrayList<Float> colorOut = new ArrayList<>();
        if (elytraIn.getItem() == ModItems.DYEABLE_ELYTRA.get())
        {
            int color = ((DyeableElytraItem) elytraIn.getItem()).getColor(elytraIn);
            float redValue = (float) (color >> 16 & 255) / 255.0F;
            float greenValue = (float) (color >> 8 & 255) / 255.0F;
            float blueValue = (float) (color & 255) / 255.0F;
            colorOut.add(redValue);
            colorOut.add(greenValue);
            colorOut.add(blueValue);
            return colorOut;
        }
        return null;
    }
}
