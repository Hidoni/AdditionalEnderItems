package com.hidoni.additionalenderitems.network;

import com.hidoni.additionalenderitems.util.MusicDiscPlayingUtil;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketStopJukebox
{
    private final BlockPos pos;

    public PacketStopJukebox(PacketBuffer buf)
    {
        pos = buf.readBlockPos();
    }

    public PacketStopJukebox(BlockPos posIn)
    {
        this.pos = posIn;
    }

    public void toBytes(PacketBuffer buf)
    {
        buf.writeBlockPos(pos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() ->
        {
            MusicDiscPlayingUtil.playEvent(pos, 0);
        });
        return true;
    }
}
