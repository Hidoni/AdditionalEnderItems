package com.hidoni.additionalenderitems.items;

import com.hidoni.additionalenderitems.AdditionalEnderItems;
import com.hidoni.additionalenderitems.config.ItemConfig;
import com.hidoni.additionalenderitems.entities.EnderTorchEntity;
import com.hidoni.additionalenderitems.setup.ModSoundEvents;
import com.hidoni.additionalenderitems.util.RayTraceUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class EnderTorchItem extends BlockItem
{
    public EnderTorchItem(Block blockIn, Properties builder)
    {
        super(blockIn, builder);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context)
    {
        return super.onItemUse(context);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        playerIn.setActiveHand(handIn);
        if (worldIn instanceof ServerWorld)
        {
            BlockRayTraceResult rayTraceResult = RayTraceUtil.getTargetBlockResult(playerIn, worldIn, ItemConfig.enderTorchMaxTravelDistance.get());
            EnderTorchEntity enderTorchEntity = new EnderTorchEntity(worldIn, playerIn.getPosX(), playerIn.getPosYHeight(0.5D), playerIn.getPosZ());
            enderTorchEntity.assignItem(itemstack);
            enderTorchEntity.moveTowards(rayTraceResult.getPos());
            worldIn.addEntity(enderTorchEntity);

            worldIn.playSound(null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), ModSoundEvents.ENDER_TORCH_LAUNCH.get(), SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
            if (!playerIn.abilities.isCreativeMode)
            {
                itemstack.shrink(1);
            }

            playerIn.addStat(Stats.ITEM_USED.get(this));
            playerIn.swing(handIn, true);
            AdditionalEnderItems.LOGGER.debug("Launched Ender Torch Entity from " + playerIn.getPosition() + " towards " + rayTraceResult.getPos());
            return ActionResult.resultSuccess(itemstack);
        }

        return ActionResult.resultConsume(itemstack);
    }
}
