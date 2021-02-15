package com.hidoni.additionalenderitems.setup;

import com.hidoni.additionalenderitems.AdditionalEnderItems;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;

public class ModSoundEvents
{
    public static RegistryObject<SoundEvent> ENDER_TORCH_LAUNCH = Registration.SOUND_EVENTS.register("ender_torch_launch", () -> new SoundEvent(new ResourceLocation(AdditionalEnderItems.MOD_ID, "ender_torch_launch")));
    public static RegistryObject<SoundEvent> ENDER_TORCH_PLACE = Registration.SOUND_EVENTS.register("ender_torch_place", () -> new SoundEvent(new ResourceLocation(AdditionalEnderItems.MOD_ID, "ender_torch_place")));
    public static RegistryObject<SoundEvent> WARP_PORTAL_CHARGE = Registration.SOUND_EVENTS.register("warp_portal_charge", () -> new SoundEvent(new ResourceLocation(AdditionalEnderItems.MOD_ID, "warp_portal_charge")));
    public static RegistryObject<SoundEvent> DRAGON_CHARGE_LAUNCH = Registration.SOUND_EVENTS.register("dragon_charge_launch", () -> new SoundEvent(new ResourceLocation(AdditionalEnderItems.MOD_ID, "dragon_charge_launch")));
    static void register()
    {
    }
}
