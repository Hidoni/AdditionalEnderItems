package com.hidoni.additionalenderitems.items;

import com.hidoni.additionalenderitems.config.Config;
import com.hidoni.additionalenderitems.config.ItemConfig;
import com.hidoni.additionalenderitems.setup.ModItems;
import net.minecraft.entity.item.EnderPearlEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class PearlStackItem extends Item
{
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
            enderpearlentity.setItem(new ItemStack(Items.ENDER_PEARL));
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
            return stack.getTag().getInt("pearls") != ItemConfig.maxPearlsInStackItem.get();
        }
        return false;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack)
    {
        if (stack.hasTag())
        {
            return 1.0D - (double)stack.getTag().getInt("pearls") / (double) ItemConfig.maxPearlsInStackItem.get();
        }
        return 1;
    }

    public static CompoundNBT createNBT()
    {
        CompoundNBT returnNBT = new CompoundNBT();
        returnNBT.putInt("pearls", 0);
        returnNBT.putInt("newPearls", 0);
        returnNBT.putBoolean("shouldReturn", true);
        return returnNBT;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack)
    {
        if (itemStack.getTag().getBoolean("shouldReturn"))
        {
            ItemStack returnStack = new ItemStack(ModItems.PEARL_STACK.get(), 1);
            returnStack.setTag(createNBT());
            returnStack.getTag().putInt("pearls", itemStack.getTag().getInt("newPearls"));
            return itemStack;
        }
        return ItemStack.EMPTY;
    }
}
