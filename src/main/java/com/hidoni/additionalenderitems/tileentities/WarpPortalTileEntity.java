package com.hidoni.additionalenderitems.tileentities;

import com.hidoni.additionalenderitems.setup.ModTileEntities;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class WarpPortalTileEntity extends TileEntity
{
    public WarpPortalTileEntity()
    {
        super(ModTileEntities.WARP_PORTAL.get());
    }

    @OnlyIn(Dist.CLIENT)
    public boolean shouldRenderFace(Direction face) {
        return face == Direction.UP;
    }
}
