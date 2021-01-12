package com.hidoni.additionalenderitems.models;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * EnderTorch - Hidoni
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class EnderTorchModel extends Model
{
    public ModelRenderer Torch;
    //public ModelRenderer Eye;

    public EnderTorchModel()
    {
        super(RenderType::getEntitySolid);
        this.textureWidth = 64;
        this.textureHeight = 32;
        //this.Eye = new ModelRenderer(this, 3, 1);
        //this.Eye.setRotationPoint(-5.5F, -9.0F, 1.0F);
        //this.Eye.addBox(0.0F, 0.0F, 0.0F, 13.0F, 13.0F, 0.01F, -4.0F, -4.0F, 0.0F);
        this.Torch = new ModelRenderer(this, 32, 12);
        this.Torch.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Torch.addBox(0.0F, 0.0F, 0.0F, 2.0F, 10.0F, 2.0F, 0.0F, 0.0F, 0.0F);
    }


    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha)
    {
        this.Torch.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        //setRotateAngle(this.Eye, 0f, (float) Math.PI * 0, (float) Math.PI * 0);
        //this.Eye.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }


    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
