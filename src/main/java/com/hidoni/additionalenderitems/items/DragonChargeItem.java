package com.hidoni.additionalenderitems.items;

import com.hidoni.additionalenderitems.AdditionalEnderItems;
import com.hidoni.additionalenderitems.config.ItemConfig;
import com.hidoni.additionalenderitems.setup.ModSoundEvents;
import com.hidoni.additionalenderitems.util.RayTraceUtil;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.DragonFireballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class DragonChargeItem extends Item
{
    public DragonChargeItem(Properties properties)
    {
        super(properties);
    }

    public static AreaEffectCloudEntity createCloudEntity(World worldIn, BlockPos posIn, LivingEntity owner)
    {
        AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(worldIn, posIn.getX(), posIn.getY() + 1, posIn.getZ());
        if (owner != null)
        {
            areaeffectcloudentity.setOwner(owner);
        }

        areaeffectcloudentity.setParticleData(ParticleTypes.DRAGON_BREATH);
        areaeffectcloudentity.setRadius(3.0F);
        areaeffectcloudentity.setDuration(600);
        areaeffectcloudentity.setRadiusPerTick((7.0F - areaeffectcloudentity.getRadius()) / (float) areaeffectcloudentity.getDuration());
        areaeffectcloudentity.addEffect(new EffectInstance(Effects.INSTANT_DAMAGE, 1, 1));

        return areaeffectcloudentity;
    }

    public static DragonFireballEntity createFireballEntity(World worldIn, PlayerEntity playerIn)
    {
        BlockPos rayTraceResult = RayTraceUtil.getTargetBlockPos(playerIn, worldIn, 20);
        Vector3d target = new Vector3d(rayTraceResult.getX() + 0.5D, rayTraceResult.getY() + 0.5D, rayTraceResult.getZ() + 0.5D);
        Vector3d vector3d = playerIn.getLook(1.0F);
        double accelX = target.getX() - (playerIn.getPosX() + vector3d.x * 1.0D);
        double accelY = target.getY() - (playerIn.getPosYHeight(0.5D));
        double accelZ = target.getZ() - (playerIn.getPosZ() + vector3d.z * 1.0D);
        AdditionalEnderItems.LOGGER.debug("Creating DragonFireballEntity with acceleration: " + new Vector3d(accelX, accelY, accelZ).toString());
        DragonFireballEntity dragonFireballEntity = new DragonFireballEntity(worldIn, playerIn, accelX, accelY, accelZ);
        dragonFireballEntity.setPosition(playerIn.getPosX() + vector3d.x, playerIn.getPosYHeight(0.5D), dragonFireballEntity.getPosZ() + vector3d.z);
        dragonFireballEntity.setShooter(playerIn);
        return dragonFireballEntity;
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context)
    {
        World world = context.getWorld();
        BlockPos blockpos = context.getPos();
        PlayerEntity player = context.getPlayer();
        AreaEffectCloudEntity dragonBreathEntity = createCloudEntity(world, blockpos, player);
        world.addEntity(dragonBreathEntity);
        world.playEvent(2006, blockpos, -1);
        if (player == null || !player.isCreative())
        {
            context.getItem().shrink(1);
        }
        return ActionResultType.func_233537_a_(world.isRemote);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (ItemConfig.allowDragonChargeToBeShotFromHand.get())
        {
            if (handIn == Hand.MAIN_HAND)
            {
                DragonFireballEntity dragonFireballEntity = createFireballEntity(worldIn, playerIn);
                worldIn.addEntity(dragonFireballEntity);
                worldIn.playSound(playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), ModSoundEvents.DRAGON_CHARGE_LAUNCH.get(), SoundCategory.PLAYERS, 10.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
                if (!playerIn.isCreative())
                {
                    itemstack.shrink(1);
                }
                return ActionResult.func_233538_a_(itemstack, worldIn.isRemote);
            }
        }
        return ActionResult.resultFail(itemstack);
    }
}
