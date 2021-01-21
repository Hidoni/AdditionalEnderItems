package com.hidoni.additionalenderitems.items;

import net.minecraft.entity.item.EnderPearlEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnderPearlItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class PearlStackItem extends Item
{
    public final int MAX_PEARLS = 255;

    public PearlStackItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        if (playerIn.getCooldownTracker().hasCooldown(Items.ENDER_PEARL))
        {
            playerIn.getCooldownTracker().setCooldown(this, (int)playerIn.getCooldownTracker().getCooldown(Items.ENDER_PEARL, 0));
        }
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (!itemstack.hasTag())
        {
            itemstack.setTag(createNBT());
        }
        int pearlCount = itemstack.getTag().getInt("pearls");
        if (pearlCount == 0)
        {
            return ActionResult.resultFail(itemstack);
        }
        worldIn.playSound(null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.ENTITY_ENDER_PEARL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
        playerIn.getCooldownTracker().setCooldown(this, 20);
        playerIn.getCooldownTracker().setCooldown(Items.ENDER_PEARL, 20);
        if (!worldIn.isRemote)
        {
            EnderPearlEntity enderpearlentity = new EnderPearlEntity(worldIn, playerIn);
            enderpearlentity.setItem(itemstack);
            enderpearlentity.func_234612_a_(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
            worldIn.addEntity(enderpearlentity);
        }

        playerIn.addStat(Stats.ITEM_USED.get(this));
        if (!playerIn.abilities.isCreativeMode)
        {
            itemstack.getTag().putInt("pearls", pearlCount - 1);
        }

        return ActionResult.func_233538_a_(itemstack, worldIn.isRemote());
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack)
    {
        if (stack.hasTag())
        {
            return stack.getTag().getInt("pearls") != MAX_PEARLS;
        }
        return false;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack)
    {
        if (stack.hasTag())
        {
            return 1.0D - (double)stack.getTag().getInt("pearls") / (double) MAX_PEARLS;
        }
        return 1;
    }

    private CompoundNBT createNBT()
    {
        CompoundNBT returnNBT = new CompoundNBT();
        returnNBT.putInt("pearls", 0);
        return returnNBT;
    }


}
