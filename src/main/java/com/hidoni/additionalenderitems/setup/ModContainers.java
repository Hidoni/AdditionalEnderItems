package com.hidoni.additionalenderitems.setup;

import com.hidoni.additionalenderitems.containers.DisenchantingBlockContainer;
import com.hidoni.additionalenderitems.tileentities.EnderTorchTileEntity;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;

public class ModContainers
{
    public static final RegistryObject<ContainerType<DisenchantingBlockContainer>> DISENCHANTING_TABLE = Registration.CONTAINERS.register("disenchanting_table", () -> IForgeContainerType.create(((windowId, inv, data) -> {
        return new DisenchantingBlockContainer(windowId, inv, IWorldPosCallable.of(inv.player.getEntityWorld(), data.readBlockPos()));
    })));

    public static void register()
    {
    }
}
