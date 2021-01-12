package com.hidoni.additionalenderitems.setup;

import com.hidoni.additionalenderitems.tileentities.EnderTorchTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;

public class ModTileEntities
{
    public static final RegistryObject<TileEntityType<EnderTorchTileEntity>> ENDER_TORCH = Registration.TILE_ENTITIES.register("ender_torch", () -> TileEntityType.Builder.create(EnderTorchTileEntity::new, ModBlocks.ENDER_TORCH.get()).build(null));

    public static void register()
    {
    }
}
