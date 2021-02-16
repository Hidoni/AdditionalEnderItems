package com.hidoni.additionalenderitems.entities;

import com.hidoni.additionalenderitems.setup.ModEntities;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.network.IPacket;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;

public class DispensedDragonFireballEntity extends DamagingProjectileEntity
{
    public DispensedDragonFireballEntity(World world, double x, double y, double z, double accelX, double accelY, double accelZ)
    {
        super(ModEntities.DISPENSED_DRAGON_FIREBALL.get(), x, y, z, accelX, accelY, accelZ, world);
    }

    public DispensedDragonFireballEntity(EntityType<DispensedDragonFireballEntity> dispensedDragonFireballEntityEntityType, World world)
    {
        super(dispensedDragonFireballEntityEntityType, world);
    }

    public DispensedDragonFireballEntity(FMLPlayMessages.SpawnEntity entity, World world)
    {
        this(world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), getAccel(entity.getVelX()), getAccel(entity.getVelY()), getAccel(entity.getVelZ()));
    }

    private static double getAccel(int velIn)
    {
        return ((double)velIn) / 8000.0D;
    }

    protected void onImpact(RayTraceResult result)
    {
        super.onImpact(result);
        Entity entity = this.func_234616_v_();
        if (result.getType() != RayTraceResult.Type.ENTITY || !((EntityRayTraceResult) result).getEntity().isEntityEqual(entity))
        {
            if (!this.world.isRemote)
            {
                List<LivingEntity> list = this.world.getEntitiesWithinAABB(LivingEntity.class, this.getBoundingBox().grow(4.0D, 2.0D, 4.0D));
                AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ());
                if (entity instanceof LivingEntity)
                {
                    areaeffectcloudentity.setOwner((LivingEntity) entity);
                }

                areaeffectcloudentity.setParticleData(ParticleTypes.DRAGON_BREATH);
                areaeffectcloudentity.setRadius(3.0F);
                areaeffectcloudentity.setDuration(600);
                areaeffectcloudentity.setRadiusPerTick((7.0F - areaeffectcloudentity.getRadius()) / (float) areaeffectcloudentity.getDuration());
                areaeffectcloudentity.addEffect(new EffectInstance(Effects.INSTANT_DAMAGE, 1, 1));
                if (!list.isEmpty())
                {
                    for (LivingEntity livingentity : list)
                    {
                        double d0 = this.getDistanceSq(livingentity);
                        if (d0 < 16.0D)
                        {
                            areaeffectcloudentity.setPosition(livingentity.getPosX(), livingentity.getPosY(), livingentity.getPosZ());
                            break;
                        }
                    }
                }

                this.world.playEvent(2006, this.getPosition(), this.isSilent() ? -1 : 1);
                this.world.addEntity(areaeffectcloudentity);
                this.remove();
            }

        }
    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean canBeCollidedWith()
    {
        return false;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        return false;
    }

    protected IParticleData getParticle()
    {
        return ParticleTypes.DRAGON_BREATH;
    }

    protected boolean isFireballFiery()
    {
        return false;
    }

    @Override
    public IPacket<?> createSpawnPacket()
    {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
