package com.hidoni.additionalenderitems.renderers.layers;

import com.hidoni.additionalenderitems.AdditionalEnderItems;
import com.hidoni.additionalenderitems.items.DyeableElytraItem;
import com.hidoni.additionalenderitems.setup.ModItems;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.model.ElytraModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class DyeableElytraLayer<T extends LivingEntity, M extends EntityModel<T>> extends ElytraLayer<T, M>
{
    private final ElytraModel<T> modelElytra = new ElytraModel<>();
    private static final ResourceLocation TEXTURE_DYEABLE_ELYTRA = new ResourceLocation(AdditionalEnderItems.MOD_ID, "textures/entity/elytra.png");
    public DyeableElytraLayer(IEntityRenderer rendererIn)
    {
        super(rendererIn);
    }


    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch)
    {
        ItemStack elytra = entitylivingbaseIn.getItemStackFromSlot(EquipmentSlotType.CHEST);
        List<Float> colors = getColors(elytra);
        if (colors != null && shouldRender(elytra, entitylivingbaseIn))
        {
            ResourceLocation elytraTexture;
            if (entitylivingbaseIn instanceof AbstractClientPlayerEntity)
            {
                AbstractClientPlayerEntity abstractclientplayerentity = (AbstractClientPlayerEntity) entitylivingbaseIn;
                if (abstractclientplayerentity.isPlayerInfoSet() && abstractclientplayerentity.getLocationElytra() != null)
                {
                    elytraTexture = abstractclientplayerentity.getLocationElytra();
                }
                else if (abstractclientplayerentity.hasPlayerInfo() && abstractclientplayerentity.getLocationCape() != null && abstractclientplayerentity.isWearing(PlayerModelPart.CAPE))
                {
                    elytraTexture = abstractclientplayerentity.getLocationCape();
                }
                else
                {
                    elytraTexture = getElytraTexture(elytra, entitylivingbaseIn);
                }
            }
            else
            {
                elytraTexture = getElytraTexture(elytra, entitylivingbaseIn);
            }

            matrixStackIn.push();
            matrixStackIn.translate(0.0D, 0.0D, 0.125D);
            this.getEntityModel().copyModelAttributesTo(this.modelElytra);
            this.modelElytra.setRotationAngles(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            IVertexBuilder ivertexbuilder = ItemRenderer.getArmorVertexBuilder(bufferIn, RenderType.getArmorCutoutNoCull(elytraTexture), false, elytra.hasEffect());
            this.modelElytra.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, colors.get(0), colors.get(1), colors.get(2), 1.0F);
            matrixStackIn.pop();
        }
    }

    @Override
    public boolean shouldRender(ItemStack stack, LivingEntity entity)
    {
        return stack.getItem() == ModItems.DYEABLE_ELYTRA.get();
    }

    @Override
    public ResourceLocation getElytraTexture(ItemStack stack, T entity) {
        if (((DyeableElytraItem)stack.getItem()).hasColor(stack))
        {
            return TEXTURE_DYEABLE_ELYTRA;
        }
        return super.getElytraTexture(stack, entity);
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
