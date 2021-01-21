package com.hidoni.additionalenderitems.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.hidoni.additionalenderitems.AdditionalEnderItems;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.File;

@Mod.EventBusSubscriber
public class Config
{
    private static final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec config;

    static
    {
        builder.comment("Additional Ender Items Common Config File");

        ItemConfig.init(builder);
        config = builder.build();
    }

    public static void loadConfig(ForgeConfigSpec config, String path)
    {
        final CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).sync().autosave().writingMode(WritingMode.REPLACE).build();
        file.load();
        config.setConfig(file);
    }

    public static void init()
    {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.config);
        loadConfig(config, FMLPaths.CONFIGDIR.get().resolve(AdditionalEnderItems.MOD_ID + "-common.toml").toString());

    }
}
