package com.hidoni.additionalenderitems.network;

import com.hidoni.additionalenderitems.AdditionalEnderItems;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class Networking
{
    private static SimpleChannel INSTANCE;
    private static int ID;
    private static final String PROTOCOL_VERSION = "1.0";

    private static int nextID()
    {
        return ID++;
    }

    public static void registerMessages()
    {
        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(AdditionalEnderItems.MOD_ID, "main"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
        INSTANCE.messageBuilder(PacketStopJukebox.class, nextID()).encoder(PacketStopJukebox::toBytes).decoder(PacketStopJukebox::new).consumer(PacketStopJukebox::handle).add();
        INSTANCE.messageBuilder(PacketStartJukebox.class, nextID()).encoder(PacketStartJukebox::toBytes).decoder(PacketStartJukebox::new).consumer(PacketStartJukebox::handle).add();
    }

    public static void sendAll(Object packet)
    {
        INSTANCE.send(PacketDistributor.ALL.noArg(), packet);
    }

    public static void sendChunkTrackers(Object packet, Chunk chunkIn)
    {
        INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> chunkIn), packet);
    }
}
