package com.hidoni.additionalenderitems.events;

import com.hidoni.additionalenderitems.items.DyeableElytraItem;
import com.hidoni.additionalenderitems.setup.ModItems;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemColorHandler
{
    @SubscribeEvent
    public static void registerItemColor(ColorHandlerEvent.Item event)
    {
        event.getItemColors().register((stack, color) -> {
            return color > 0 ? -1 : ((IDyeableArmorItem)stack.getItem()).getColor(stack);
        }, ModItems.DYEABLE_ELYTRA.get());
    }
}
