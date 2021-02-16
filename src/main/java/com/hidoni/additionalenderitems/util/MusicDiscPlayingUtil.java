package com.hidoni.additionalenderitems.util;

import com.google.common.collect.Maps;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Map;

// This is a workaround for the vanilla implementation of jukeboxes not having a way to disable sound attenuation, this code is mostly copy pasting of functions from WorldRenderer
@OnlyIn(Dist.CLIENT)
public class MusicDiscPlayingUtil
{
    private static final Map<BlockPos, ISound> mapSoundPositions = Maps.newHashMap();
    private static Minecraft mc = null;

    public static void setMinecraft(Minecraft mcIn)
    {
        mc = mcIn;
    }

    public static void playEvent(BlockPos blockPosIn, int data)
    {
        if (Item.getItemById(data) instanceof MusicDiscItem)
        {
            playRecord(((MusicDiscItem) Item.getItemById(data)).getSound(), blockPosIn, (MusicDiscItem) Item.getItemById(data));
        }
        else
        {
            playRecord(null, blockPosIn, null);
        }
    }

    public static void playRecord(@Nullable SoundEvent soundIn, BlockPos pos, @Nullable MusicDiscItem musicDiscItem)
    {
        ISound isound = mapSoundPositions.get(pos);
        if (isound != null)
        {
            mc.getSoundHandler().stop(isound);
            mapSoundPositions.remove(pos);
        }

        if (soundIn != null)
        {
            MusicDiscItem musicdiscitem = musicDiscItem;
            if (musicdiscitem != null)
            {
                mc.ingameGUI.func_238451_a_(musicdiscitem.getDescription());
            }

            ISound simplesound = new SimpleSound(soundIn.getName(), SoundCategory.RECORDS, 4.0F, 1.0F, false, 0, ISound.AttenuationType.NONE, pos.getX(), pos.getY(), pos.getZ(), false);
            mapSoundPositions.put(pos, simplesound);
            mc.getSoundHandler().play(simplesound);
        }

        setPartying(mc.world, pos, soundIn != null);
    }

    private static void setPartying(World worldIn, BlockPos pos, boolean isPartying)
    {
        for (LivingEntity livingentity : worldIn.getEntitiesWithinAABB(LivingEntity.class, (new AxisAlignedBB(pos)).grow(3.0D)))
        {
            livingentity.setPartying(pos, isPartying);
        }
    }

    public static boolean alreadyPlaying(BlockPos pos)
    {
        return mapSoundPositions.containsKey(pos);
    }
}