package com.hidoni.additionalenderitems;

import com.hidoni.additionalenderitems.config.Config;
import com.hidoni.additionalenderitems.config.EntityConfig;
import com.hidoni.additionalenderitems.events.BiomeLoadingHandler;
import com.hidoni.additionalenderitems.events.ClientEventHandler;
import com.hidoni.additionalenderitems.events.TeleportEventHandler;
import com.hidoni.additionalenderitems.network.Networking;
import com.hidoni.additionalenderitems.setup.ModEntities;
import com.hidoni.additionalenderitems.setup.Registration;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(AdditionalEnderItems.MOD_ID)
public class AdditionalEnderItems
{
    public static final String MOD_ID = "additionalenderitems";
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    public AdditionalEnderItems()
    {
        Registration.register();

        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        Config.init();

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        Networking.registerMessages();
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        //LOGGER.info("Registering Ores...");
        //OreGeneration.registerOres();
        LOGGER.info("Registering To Event Busses...");
        MinecraftForge.EVENT_BUS.register(new TeleportEventHandler());
        MinecraftForge.EVENT_BUS.register(new BiomeLoadingHandler());
        GlobalEntityTypeAttributes.put(ModEntities.ENDER_PHANTOM.get(), MonsterEntity.func_234295_eP_().createMutableAttribute(Attributes.MAX_HEALTH, EntityConfig.enderPhantomBaseHealth.get()).create());
    }

    private void doClientStuff(final FMLClientSetupEvent event)
    {
        ClientEventHandler.handleClientLoading(event);
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {

    }

    private void processIMC(final InterModProcessEvent event)
    {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event)
    {

    }
}
