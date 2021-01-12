package com.hidoni.additionalenderitems.renderers;

import com.hidoni.additionalenderitems.AdditionalEnderItems;
import com.hidoni.additionalenderitems.models.EnderTorchModel;
import com.hidoni.additionalenderitems.tileentities.EnderTorchTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class EnderTorchTileEntityRenderer extends TileEntityRenderer<EnderTorchTileEntity>
{
    public static final RenderMaterial TEXTURE_TORCH = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation(AdditionalEnderItems.MOD_ID, "entity/ender_torch"));
    private final EnderTorchModel enderTorchModel = new EnderTorchModel();

    public EnderTorchTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn)
    {
        super(rendererDispatcherIn);
    }


    @Override
    public void render(EnderTorchTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
    {
        matrixStackIn.push();
        double modified = Math.sin((double) tileEntityIn.ticks / 10);
        float y = 0.625F + (tileEntityIn.ON_SOLID_GROUND ? 0 : (float) MathHelper.lerp(modified, 0.05, -0.25));
        matrixStackIn.translate(0.44F, y, 0.5625F);
        matrixStackIn.rotate(Vector3f.XP.rotationDegrees(180));
        IVertexBuilder ivertexbuilder = TEXTURE_TORCH.getBuffer(bufferIn, RenderType::getEntityCutout);
        enderTorchModel.render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStackIn.pop();
        float f1 = tileEntityIn.nextEyeAngle - tileEntityIn.eyeAngle;
        while (f1 >= (float) Math.PI)
        {
            f1 -= ((float) Math.PI * 2F);
        }
        while (f1 < -(float) Math.PI)
        {
            f1 += ((float) Math.PI * 2F);
        }

        float f2 = tileEntityIn.eyeAngle + f1 * partialTicks;
        matrixStackIn.push();
        matrixStackIn.translate(0.50625F, 0.11F + y, 0.5F);
        matrixStackIn.scale(0.25F, 0.25F, 0.25F);
        matrixStackIn.rotate(Vector3f.YP.rotation(-f2 - ((float) Math.PI / 2)));
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack stack = new ItemStack(Items.ENDER_EYE);
        IBakedModel ibakedmodel = itemRenderer.getItemModelWithOverrides(stack, tileEntityIn.getWorld(), null);
        itemRenderer.renderItem(stack, ItemCameraTransforms.TransformType.FIXED, true, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, ibakedmodel);
        matrixStackIn.pop();
    }
}
