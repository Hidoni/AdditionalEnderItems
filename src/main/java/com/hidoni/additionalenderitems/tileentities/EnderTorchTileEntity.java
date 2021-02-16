package com.hidoni.additionalenderitems.tileentities;

import com.hidoni.additionalenderitems.setup.ModBlocks;
import com.hidoni.additionalenderitems.setup.ModTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.state.BooleanProperty;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nullable;

public class EnderTorchTileEntity extends TileEntity implements ITickableTileEntity
{
    public long ticks = 0;
    public boolean ON_SOLID_GROUND = false;
    public float nextEyeAngle;
    public float eyeAngle;
    private float rotation;

    public EnderTorchTileEntity()
    {
        super(ModTileEntities.ENDER_TORCH.get());
        ON_SOLID_GROUND = false;
    }

    public EnderTorchTileEntity(boolean floatState)
    {
        super(ModTileEntities.ENDER_TORCH.get());
        ON_SOLID_GROUND = floatState;
    }

    @Override
    public void tick()
    {
        this.eyeAngle = this.nextEyeAngle;
        ticks++;
        PlayerEntity playerentity = this.world.getClosestPlayer((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D, 3.0D, false);
        if (playerentity != null)
        {
            double d0 = playerentity.getPosX() - ((double) this.pos.getX() + 0.5D);
            double d1 = playerentity.getPosZ() - ((double) this.pos.getZ() + 0.5D);
            this.rotation = (float) MathHelper.atan2(d1, d0);
        }
        else
        {
            this.rotation += 0.02f;
        }

        while (this.rotation >= (float) Math.PI)
        {
            this.rotation -= ((float) Math.PI * 2F);
        }

        while (this.rotation < -(float) Math.PI)
        {
            this.rotation += ((float) Math.PI * 2F);
        }

        float f2 = this.rotation - this.nextEyeAngle;
        while (f2 >= (float) Math.PI)
        {
            f2 -= ((float) Math.PI * 2F);
        }
        while (f2 < -(float) Math.PI)
        {
            f2 += ((float) Math.PI * 2F);
        }

        this.nextEyeAngle += f2 * 0.4F;
    }

    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        return super.write(compound);
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt)
    {
        super.read(state, nbt);
        this.ON_SOLID_GROUND = state.get((BooleanProperty) ModBlocks.ENDER_TORCH.get().getStateContainer().getProperty("on_solid_ground"));
        this.ticks = 0;
    }

    @Override
    public CompoundNBT getUpdateTag()
    {
        return write(new CompoundNBT());
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket()
    {
        return new SUpdateTileEntityPacket(getPos(), 0, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt)
    {
        this.read(world.getBlockState(pkt.getPos()), pkt.getNbtCompound());
    }
}
