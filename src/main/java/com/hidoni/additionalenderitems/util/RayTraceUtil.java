package com.hidoni.additionalenderitems.util;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class RayTraceUtil
{

    public static BlockState getTargetBlock(PlayerEntity player, World world, int maxdistance)
    {
        BlockPos blockpos = getTargetBlockPos(player, world, maxdistance);
        if (blockpos == null)
        {
            return null;
        }
        return world.getBlockState(blockpos);
    }

    public static BlockPos getTargetBlockPos(PlayerEntity player, World world, int maxdistance)
    {
        BlockRayTraceResult rayTraceResult = getTargetBlockResult(player, world, maxdistance);
        if (rayTraceResult != null)
        {
            return new BlockPos(rayTraceResult.getHitVec().getX(), rayTraceResult.getHitVec().getY(), rayTraceResult.getHitVec().getZ());
        }
        return null;
    }

    public static BlockRayTraceResult getTargetBlockResult(PlayerEntity player, World world, int maxdistance)
    {
        Vector3d vec = player.getPositionVec();
        Vector3d vec3 = new Vector3d(vec.x, vec.y + player.getEyeHeight(), vec.z);
        Vector3d vec3a = player.getLook(1.0F);
        Vector3d vec3b = vec3.add(vec3a.getX() * maxdistance, vec3a.getY() * maxdistance, vec3a.getZ() * maxdistance);

        BlockRayTraceResult rayTraceResult = world.rayTraceBlocks(new RayTraceContext(vec3, vec3b, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.ANY, player));


        Vector3d hitVec = rayTraceResult.getHitVec();
        double xm = hitVec.getX();
        double ym = hitVec.getY();
        double zm = hitVec.getZ();

        return new BlockRayTraceResult(hitVec, rayTraceResult.getFace(), new BlockPos(xm, ym, zm), false);
    }

}