package com.hidoni.additionalenderitems.events;

import com.hidoni.additionalenderitems.setup.ModEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Map;

public class TeleportEventHandler
{
    @SubscribeEvent
    public void cancelPlayerTeleportDamage(EnderTeleportEvent event)
    {
        if (event.getEntity() instanceof LivingEntity)
        {
            LivingEntity entity = event.getEntityLiving();
            Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(entity.getItemStackFromSlot(EquipmentSlotType.FEET));
            if (map.containsKey(ModEnchantments.WARP_RESISTANCE.get()))
            {
                event.setAttackDamage(0f);
            }
        }
    }
}
