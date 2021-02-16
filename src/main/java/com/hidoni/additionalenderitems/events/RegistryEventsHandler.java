package com.hidoni.additionalenderitems.events;


import com.hidoni.additionalenderitems.blocks.WarpPortalBlock;
import com.hidoni.additionalenderitems.entities.DispensedDragonFireballEntity;
import com.hidoni.additionalenderitems.entities.EnderPhantomEntity;
import com.hidoni.additionalenderitems.items.ModdedSpawnEggItem;
import com.hidoni.additionalenderitems.setup.ModBlocks;
import com.hidoni.additionalenderitems.setup.ModEntities;
import com.hidoni.additionalenderitems.setup.ModItems;
import net.minecraft.block.*;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.OptionalDispenseBehavior;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.DragonFireballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

// You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
// Event bus for receiving Registry Events)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryEventsHandler
{
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPostRegisterEntities(final RegistryEvent.Register<EntityType<?>> event)
    {
        ModdedSpawnEggItem.initUnaddedEggs();
        EntitySpawnPlacementRegistry.register(ModEntities.ENDER_PHANTOM.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EnderPhantomEntity::canSpawnOn);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPostRegisterBlocks(final RegistryEvent.Register<Block> event)
    {
        OptionalDispenseBehavior warpPortalChargeBhavior = new OptionalDispenseBehavior()
        {
            /**
             * Dispense the specified stack, play the dispense sound and spawn particles.
             */
            public ItemStack dispenseStack(IBlockSource source, ItemStack stack)
            {
                Direction direction = source.getBlockState().get(DispenserBlock.FACING);
                BlockPos blockpos = source.getBlockPos().offset(direction);
                World world = source.getWorld();
                BlockState blockstate = world.getBlockState(blockpos);
                this.setSuccessful(true);
                if (blockstate.isIn(ModBlocks.WARP_PORTAL.get()))
                {
                    if (blockstate.get(WarpPortalBlock.CHARGES) != WarpPortalBlock.MAX_CHARGES)
                    {
                        WarpPortalBlock.fuelPortal(world, blockpos, blockstate, stack);
                        stack.shrink(1);
                    }
                    else
                    {
                        this.setSuccessful(false);
                    }

                    return stack;
                }
                else
                {
                    return super.dispenseStack(source, stack);
                }
            }
        };
        DispenserBlock.registerDispenseBehavior(Items.ENDER_PEARL, warpPortalChargeBhavior);
        DispenserBlock.registerDispenseBehavior(Items.ENDER_EYE, warpPortalChargeBhavior);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPostRegisterItems(final RegistryEvent.Register<Item> event)
    {
        DefaultDispenseItemBehavior dragonChargeBehavior = new DefaultDispenseItemBehavior()
        {
            /**
             * Dispense the specified stack, play the dispense sound and spawn particles.
             */
            public ItemStack dispenseStack(IBlockSource source, ItemStack stack)
            {
                Direction direction = source.getBlockState().get(DispenserBlock.FACING);
                IPosition iposition = DispenserBlock.getDispensePosition(source);
                double d0 = iposition.getX() + (double) ((float) direction.getXOffset() * 0.3F);
                double d1 = iposition.getY() + (double) ((float) direction.getYOffset() * 0.3F);
                double d2 = iposition.getZ() + (double) ((float) direction.getZOffset() * 0.3F);
                World world = source.getWorld();
                Random random = world.rand;
                double d3 = random.nextGaussian() * 0.05D + (double) direction.getXOffset();
                double d4 = random.nextGaussian() * 0.05D + (double) direction.getYOffset();
                double d5 = random.nextGaussian() * 0.05D + (double) direction.getZOffset();
                world.addEntity(new DispensedDragonFireballEntity(world, d0, d1, d2, d3, d4, d5));
                stack.shrink(1);
                return stack;
            }
        };

        DispenserBlock.registerDispenseBehavior(ModItems.DRAGON_CHARGE.get(), dragonChargeBehavior);
    }
}