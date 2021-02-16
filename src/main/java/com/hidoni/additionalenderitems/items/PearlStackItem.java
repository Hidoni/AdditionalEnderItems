package com.hidoni.additionalenderitems.items;

import com.hidoni.additionalenderitems.config.ItemConfig;
import com.hidoni.additionalenderitems.setup.ModItems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EnderPearlEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class PearlStackItem extends Item
{

    public static final String PEARL_TOOLTIP = "item.additionalenderitems.pearl_stack_string";

    public PearlStackItem(Properties properties)
    {
        super(properties);
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
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        ItemStack heldItem = playerIn.getHeldItem(handIn);
        if (!heldItem.hasTag())
        {
            heldItem.setTag(createNBT());
        }

        if (ItemConfig.refillPearlsByCrouch.get() && playerIn.isCrouching())
        {
            for (ItemStack inventoryItem : playerIn.inventory.mainInventory)
            {
                if (inventoryItem.getItem() == Items.ENDER_PEARL)
                {
                    int currentPearls = heldItem.getTag().getInt("pearls");
                    int maxPearlsToTake = ItemConfig.maxPearlsInStackItem.get() - currentPearls;
                    int otherItemPearls = inventoryItem.getCount();
                    if (otherItemPearls >= maxPearlsToTake)
                    {
                        inventoryItem.shrink(maxPearlsToTake);
                        heldItem.getTag().putInt("pearls", currentPearls + maxPearlsToTake);
                    }
                    else
                    {
                        inventoryItem.setCount(0);
                        heldItem.getTag().putInt("pearls", currentPearls + otherItemPearls);
                    }
                }
            }
        }
        else
        {
            if (playerIn.getCooldownTracker().hasCooldown(Items.ENDER_PEARL))
            {
                playerIn.getCooldownTracker().setCooldown(this, (int) playerIn.getCooldownTracker().getCooldown(Items.ENDER_PEARL, 1.0F));
            }
            int pearlCount = heldItem.getTag().getInt("pearls");
            if (pearlCount == 0)
            {
                return ActionResult.resultFail(heldItem);
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
                heldItem.getTag().putInt("pearls", pearlCount - 1);
            }
        }
        return ActionResult.func_233538_a_(heldItem, worldIn.isRemote());
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
            return 1.0D - (double) stack.getTag().getInt("pearls") / (double) ItemConfig.maxPearlsInStackItem.get();
        }
        return 1;
    }

    @Override
    public boolean hasContainerItem(ItemStack itemStack)
    {
        return itemStack.hasTag() && itemStack.getTag().getBoolean("shouldReturn");
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack)
    {
        if (itemStack.hasTag() && itemStack.getTag().getBoolean("shouldReturn"))
        {
            ItemStack returnStack = new ItemStack(ModItems.PEARL_STACK.get(), 1);
            returnStack.setTag(createNBT());
            returnStack.getTag().putInt("pearls", itemStack.getTag().getInt("newPearls"));
            return returnStack;
        }
        return ItemStack.EMPTY;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if (!stack.hasTag())
        {
            stack.setTag(createNBT());
        }
        tooltip.add(new StringTextComponent(String.valueOf(stack.getTag().getInt("pearls")) + '/' + ItemConfig.maxPearlsInStackItem.get().toString() + " " + (new TranslationTextComponent(PEARL_TOOLTIP)).getString()).setStyle(Style.EMPTY.createStyleFromFormattings(TextFormatting.GRAY)));
    }

    @Override
    public boolean hasEffect(ItemStack stack)
    {
        if (!stack.hasTag())
        {
            return super.hasEffect(stack);
        }
        else if (stack.getTag().getInt("pearls") == 0)
        {
            return super.hasEffect(stack);
        }
        return true; // If we have any pearls in the sack, make it glow.
    }
}
