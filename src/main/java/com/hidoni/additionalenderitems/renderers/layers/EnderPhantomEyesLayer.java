package com.hidoni.additionalenderitems.renderers.layers;

import com.hidoni.additionalenderitems.AdditionalEnderItems;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.AbstractEyesLayer;
import net.minecraft.client.renderer.entity.model.PhantomModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EnderPhantomEyesLayer<T extends Entity> extends AbstractEyesLayer<T, PhantomModel<T>>
{
    private static final RenderType field_229138_a_ = RenderType.getEyes(new ResourceLocation(AdditionalEnderItems.MOD_ID, "textures/entity/phantom_eyes.png"));

    public EnderPhantomEyesLayer(IEntityRenderer<T, PhantomModel<T>> p_i50928_1_) {
        super(p_i50928_1_);
    }

    public RenderType getRenderType() {
        return field_229138_a_;
    }
}
