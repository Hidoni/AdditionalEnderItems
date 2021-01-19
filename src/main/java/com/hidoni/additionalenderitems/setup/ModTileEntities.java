package com.hidoni.additionalenderitems.setup;

import com.hidoni.additionalenderitems.tileentities.EnderJukeboxTileEntity;
import com.hidoni.additionalenderitems.tileentities.EnderTorchTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;

public class ModTileEntities
{
    public static final RegistryObject<TileEntityType<EnderTorchTileEntity>> ENDER_TORCH = Registration.TILE_ENTITIES.register("ender_torch", () -> TileEntityType.Builder.create(EnderTorchTileEntity::new, ModBlocks.ENDER_TORCH.get()).build(null));
    public static final RegistryObject<TileEntityType<EnderJukeboxTileEntity>> ENDER_JUKEBOX = Registration.TILE_ENTITIES.register("ender_jukebox", () -> TileEntityType.Builder.create(EnderJukeboxTileEntity::new, ModBlocks.ENDER_JUKEBOX.get()).build(null));

    public static void register()
    {
    }
}
