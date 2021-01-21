package com.hidoni.additionalenderitems.network;

import com.hidoni.additionalenderitems.util.MusicDiscPlayingUtil;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketStartJukebox
{
    private final BlockPos pos;
    private int data;

    public PacketStartJukebox(PacketBuffer buf)
    {
        pos = buf.readBlockPos();
        data = buf.readInt();
    }

    public PacketStartJukebox(BlockPos posIn, int dataIn)
    {
        this.pos = posIn;
        this.data = dataIn;
    }

    public void toBytes(PacketBuffer buf)
    {
        buf.writeBlockPos(pos);
        buf.writeInt(data);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() ->
        {
            if (!MusicDiscPlayingUtil.alreadyPlaying(pos))
            {
                MusicDiscPlayingUtil.playEvent(pos, data);
            }
        });
        return true;
    }
}
