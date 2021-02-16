package com.hidoni.additionalenderitems.entities;

import com.hidoni.additionalenderitems.setup.ModBlocks;
import com.hidoni.additionalenderitems.setup.ModEntities;
import com.hidoni.additionalenderitems.setup.ModItems;
import com.hidoni.additionalenderitems.setup.ModSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.ArrayList;
import java.util.Arrays;

@OnlyIn(
        value = Dist.CLIENT,
        _interface = IRendersAsItem.class
)
public class EnderTorchEntity extends Entity implements IRendersAsItem
{
    private static final DataParameter<ItemStack> itemStackDataParameter = EntityDataManager.createKey(EnderTorchEntity.class, DataSerializers.ITEMSTACK);
    private double targetX;
    private double targetY;
    private double targetZ;
    private int despawnTimer;

    public EnderTorchEntity(EntityType<? extends EnderTorchEntity> entityTypeIn, World worldIn)
    {
        super(entityTypeIn, worldIn);
    }

    public EnderTorchEntity(World worldIn, double x, double y, double z)
    {
        this(ModEntities.ENDER_TORCH.get(), worldIn);
        this.despawnTimer = 0;
        this.setPosition(x, y, z);
    }

    public EnderTorchEntity(FMLPlayMessages.SpawnEntity packet, World worldIn)
    {
        super(ModEntities.ENDER_TORCH.get(), worldIn);
        this.setPosition(packet.getPosX(), packet.getPosY(), packet.getPosZ());
    }

    protected static float func_234614_e_(float p_234614_0_, float p_234614_1_)
    {
        while (p_234614_1_ - p_234614_0_ < -180.0F)
        {
            p_234614_0_ -= 360.0F;
        }

        while (p_234614_1_ - p_234614_0_ >= 180.0F)
        {
            p_234614_0_ += 360.0F;
        }

        return MathHelper.lerp(0.2F, p_234614_0_, p_234614_1_);
    }

    @Override
    protected void registerData()
    {
        this.getDataManager().register(itemStackDataParameter, ItemStack.EMPTY);
    }

    @Override
    public void readAdditional(CompoundNBT compound)
    {
        ItemStack itemstack = ItemStack.read(compound.getCompound("Item"));
        this.assignItem(itemstack);
    }

    @Override
    public void writeAdditional(CompoundNBT compound)
    {
        ItemStack itemstack = this.getItemStack();
        if (!itemstack.isEmpty())
        {
            compound.put("Item", itemstack.write(new CompoundNBT()));
        }
    }

    private ItemStack getItemStack()
    {
        return this.getDataManager().get(itemStackDataParameter);
    }

    public void assignItem(ItemStack itemStack)
    {
        if (itemStack.getItem() != ModItems.ENDER_TORCH.get() || itemStack.hasTag())
        {
            this.getDataManager().set(itemStackDataParameter, Util.make(itemStack.copy(), (stack) ->
            {
                stack.setCount(1);
            }));
        }
    }

    @Override
    public IPacket<?> createSpawnPacket()
    {
        //return new SSpawnObjectPacket(this);
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public ItemStack getItem()
    {
        ItemStack itemstack = this.getItemStack();
        return itemstack.isEmpty() ? new ItemStack(ModItems.ENDER_TORCH.get()) : itemstack;
    }

    @Override
    public float getBrightness()
    {
        return 1.0F;
    }

    /**
     * Returns true if it's possible to attack this entity with an item.
     */
    @Override
    public boolean canBeAttackedWithItem()
    {
        return false;
    }

    /**
     * Checks if the entity is in range to render.
     */
    @OnlyIn(Dist.CLIENT)
    @Override
    public boolean isInRangeToRenderDist(double distance)
    {
        double d0 = this.getBoundingBox().getAverageEdgeLength() * 4.0D;
        if (Double.isNaN(d0))
        {
            d0 = 4.0D;
        }

        d0 = d0 * 64.0D;
        return distance < d0 * d0;
    }

    public void moveTowards(BlockPos pos)
    {
        double posX = pos.getX();
        double posY = pos.getY();
        double posZ = pos.getZ();
        double xOffset = posX - this.getPosX();
        double zOffset = posZ - this.getPosZ();
        double yOffset = posY - this.getPosY();
        float f = MathHelper.sqrt(xOffset * xOffset + zOffset * zOffset);

        if (xOffset > 0)
        {
            this.targetX = posX - 0.5d;
        }
        else
        {
            this.targetX = posX + 0.5d;
        }
        if (yOffset > 0)
        {
            this.targetY = posY + 0.5d;
        }
        else
        {
            this.targetY = posY - 0.5d;
        }
        if (zOffset > 0)
        {
            this.targetZ = posZ - 0.5d;
        }
        else
        {
            this.targetZ = posZ + 0.5d;
        }
        this.despawnTimer = 0;
    }

    /**
     * Updates the entity motion clientside, called by packets from the server
     */
    @OnlyIn(Dist.CLIENT)
    @Override
    public void setVelocity(double x, double y, double z)
    {
        this.setMotion(x, y, z);
        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
        {
            float f = MathHelper.sqrt(x * x + z * z);
            this.rotationYaw = (float) (MathHelper.atan2(x, z) * (double) (180F / (float) Math.PI));
            this.rotationPitch = (float) (MathHelper.atan2(y, f) * (double) (180F / (float) Math.PI));
            this.prevRotationYaw = this.rotationYaw;
            this.prevRotationPitch = this.rotationPitch;
        }

    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void tick()
    {
        super.tick();
        Vector3d vector3d = this.getMotion();
        double d0 = this.getPosX() + vector3d.x;
        double d1 = this.getPosY() + vector3d.y;
        double d2 = this.getPosZ() + vector3d.z;
        float f = MathHelper.sqrt(horizontalMag(vector3d));
        this.rotationPitch = func_234614_e_(this.prevRotationPitch, (float) (MathHelper.atan2(vector3d.y, f) * (double) (180F / (float) Math.PI)));
        this.rotationYaw = func_234614_e_(this.prevRotationYaw, (float) (MathHelper.atan2(vector3d.x, vector3d.z) * (double) (180F / (float) Math.PI)));
        if (!this.world.isRemote)
        {
            double d3 = this.targetX - d0;
            double d4 = this.targetZ - d2;
            float f1 = (float) Math.sqrt(d3 * d3 + d4 * d4);
            float f2 = (float) MathHelper.atan2(d4, d3);
            double d5 = MathHelper.lerp(0.0025D, f, f1);
            double d6 = vector3d.y;
            if (f1 < 1.0F)
            {
                d5 *= 0.8D;
                d6 *= 0.8D;
            }

            double j = this.getPosY() < this.targetY ? 0.5F : -0.5F;
            vector3d = new Vector3d(Math.cos(f2) * d5, d6 + (j - d6) * (double) 0.015F, Math.sin(f2) * d5);
            this.setMotion(vector3d);
        }

        float f3 = 0.25F;
        if (this.isInWater())
        {
            for (int i = 0; i < 4; ++i)
            {
                this.world.addParticle(ParticleTypes.BUBBLE, d0 - vector3d.x * 0.25D, d1 - vector3d.y * 0.25D, d2 - vector3d.z * 0.25D, vector3d.x, vector3d.y, vector3d.z);
            }
        }
        else
        {
            this.world.addParticle(ParticleTypes.PORTAL, d0 - vector3d.x * 0.25D + this.rand.nextDouble() * 0.6D - 0.3D, d1 - vector3d.y * 0.25D - 0.5D, d2 - vector3d.z * 0.25D + this.rand.nextDouble() * 0.6D - 0.3D, vector3d.x, vector3d.y, vector3d.z);
        }

        if (!this.world.isRemote)
        {
            this.setPosition(d0, d1, d2);
            ++this.despawnTimer;
            if (this.despawnTimer > 80 && !this.world.isRemote)
            {
                this.playSound(ModSoundEvents.ENDER_TORCH_PLACE.get(), 1.0F, 1.0F);
                this.remove();
                ArrayList<BlockState> validAirBlocks = new ArrayList<BlockState>(Arrays.asList(Blocks.AIR.getDefaultState(), Blocks.CAVE_AIR.getDefaultState()));
                if (validAirBlocks.contains(this.world.getBlockState(this.getPosition())))
                {
                    BooleanProperty onSolidGround = (BooleanProperty) ModBlocks.ENDER_TORCH.get().getStateContainer().getProperty("on_solid_ground");
                    BlockPos blockBelow = this.getPosition().down();
                    boolean blockBelowIsAir = this.world.getBlockState(blockBelow).isAir(this.world, blockBelow);
                    this.world.setBlockState(this.getPosition(), ModBlocks.ENDER_TORCH.get().getDefaultState().with(onSolidGround, !blockBelowIsAir));
                }
                else if (validAirBlocks.contains(this.world.getBlockState(this.getPosition().up())))
                {
                    this.world.setBlockState(this.getPosition().up(), ModBlocks.ENDER_TORCH.get().getDefaultState());
                }
                else
                {
                    this.world.addEntity(new ItemEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ(), this.getItem()));
                }
            }
        }
        else
        {
            this.setRawPosition(d0, d1, d2);
        }

    }
}
