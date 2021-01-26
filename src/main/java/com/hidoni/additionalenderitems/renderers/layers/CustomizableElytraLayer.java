package com.hidoni.additionalenderitems.renderers.layers;

import com.hidoni.additionalenderitems.AdditionalEnderItems;
import com.hidoni.additionalenderitems.items.CustomizableElytraItem;
import com.hidoni.additionalenderitems.setup.ModItems;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.model.ElytraModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.tileentity.BannerTileEntity;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class CustomizableElytraLayer<T extends LivingEntity, M extends EntityModel<T>> extends ElytraLayer<T, M>
{
    private final ElytraModel<T> modelElytra = new ElytraModel<>();
    private static final ResourceLocation TEXTURE_DYEABLE_ELYTRA = new ResourceLocation(AdditionalEnderItems.MOD_ID, "textures/entity/elytra.png");
    private static final ResourceLocation TEXTURE_BANNER_ELYTRA_BASE = new ResourceLocation(AdditionalEnderItems.MOD_ID, "textures/entity/elytra_banner.png");
    public CustomizableElytraLayer(IEntityRenderer rendererIn)
    {
        super(rendererIn);
    }


    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch)
    {
        ItemStack elytra = entitylivingbaseIn.getItemStackFromSlot(EquipmentSlotType.CHEST);
        CompoundNBT blockEntityTag = elytra.getChildTag("BlockEntityTag");
        if (blockEntityTag == null)
        {
            renderDyed(matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, elytra);
        }
        else
        {
            renderBanner(matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, elytra);
        }
    }

    public void renderDyed(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack elytra)
    {
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

    public void renderBanner(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack elytra)
    {
        ResourceLocation elytraTexture = getElytraTexture(elytra, entitylivingbaseIn);
        matrixStackIn.push();
        matrixStackIn.translate(0.0D, 0.0D, 0.125D);
        this.getEntityModel().copyModelAttributesTo(this.modelElytra);
        this.modelElytra.setRotationAngles(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        IVertexBuilder ivertexbuilder = ItemRenderer.getArmorVertexBuilder(bufferIn, RenderType.getArmorCutoutNoCull(elytraTexture), false, elytra.hasEffect());
        this.modelElytra.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        /*p_241717_4_.render(p_241717_0_, p_241717_5_.getItemRendererBuffer(p_241717_1_, RenderType::getEntitySolid, p_241717_8_), p_241717_2_, p_241717_3_);*/
        List<Pair<BannerPattern, DyeColor>> list = BannerTileEntity.getPatternColorData(ShieldItem.getColor(elytra), BannerTileEntity.getPatternData(elytra));

        for(int i = 0; i < 17 && i < list.size(); ++i) {
            Pair<BannerPattern, DyeColor> pair = list.get(i);
            float[] afloat = pair.getSecond().getColorComponentValues();
            RenderMaterial rendermaterial = new RenderMaterial(Atlases.BANNER_ATLAS, pair.getFirst().getTextureLocation(true));
            this.modelElytra.render(matrixStackIn, rendermaterial.getBuffer(bufferIn, RenderType::getEntityNoOutline), packedLightIn, OverlayTexture.NO_OVERLAY, afloat[0], afloat[1], afloat[2], 1.0F);
        }
        matrixStackIn.pop();
    }

    @Override
    public boolean shouldRender(ItemStack stack, LivingEntity entity)
    {
        return stack.getItem() == ModItems.CUSTOMIZABLE_ELYTRA.get();
    }

    @Override
    public ResourceLocation getElytraTexture(ItemStack stack, T entity) {
        if (((CustomizableElytraItem)stack.getItem()).hasColor(stack))
        {
            return TEXTURE_DYEABLE_ELYTRA;
        }
        return super.getElytraTexture(stack, entity);
    }

    public List<Float> getColors(ItemStack elytraIn)
    {
        ArrayList<Float> colorOut = new ArrayList<>();
        if (elytraIn.getItem() == ModItems.CUSTOMIZABLE_ELYTRA.get())
        {
            int color = ((CustomizableElytraItem) elytraIn.getItem()).getColor(elytraIn);
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
