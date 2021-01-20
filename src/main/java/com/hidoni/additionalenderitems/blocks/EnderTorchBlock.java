package com.hidoni.additionalenderitems.blocks;

import com.hidoni.additionalenderitems.tileentities.EnderTorchTileEntity;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;

public class EnderTorchBlock extends TorchBlock implements IWaterLoggable
{

    public static final BooleanProperty ON_SOLID_GROUND = BooleanProperty.create("on_solid_ground");
    public static final BooleanProperty WATERLOGGED = BooleanProperty.create("waterlogged");

    public EnderTorchBlock(Properties properties, IParticleData particleData)
    {
        super(properties, particleData);
        this.setDefaultState(this.getStateContainer().getBaseState().with(ON_SOLID_GROUND, true).with(WATERLOGGED, false));
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos)
    {
        return true;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(ON_SOLID_GROUND, WATERLOGGED);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        if (rand.nextBoolean())
        {
            double d0 = (double) pos.getX() + 0.5D;
            double d1 = (double) pos.getY() + 0.7D;
            double d2 = (double) pos.getZ() + 0.5D;
            double d3 = ((double) rand.nextFloat() - 0.5D) * 0.05D;
            double d4 = ((double) rand.nextFloat() - 0.5D) * 0.05D;
            double d5 = ((double) rand.nextFloat() - 0.5D) * 0.05D;
            worldIn.addParticle(ParticleTypes.REVERSE_PORTAL, d0, d1, d2, d3, d4, d5);
        }
    }

    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new EnderTorchTileEntity(state.get(ON_SOLID_GROUND));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        BlockPos pos = context.getPos();
        FluidState fluidstate = context.getWorld().getFluidState(pos);
        Boolean hasEnoughSolid = hasEnoughSolidSide(context.getWorld(), pos.down(), Direction.UP);
        return this.getDefaultState().with(ON_SOLID_GROUND, hasEnoughSolid).with(WATERLOGGED, fluidstate.getFluid() == Fluids.WATER);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
    {
        if (stateIn.get(WATERLOGGED))
        {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }
        if (facingPos.up().equals(currentPos))
        {
            Boolean stateIsSolid = !facingState.isAir(worldIn, facingPos) && hasEnoughSolidSide(worldIn, facingPos, Direction.UP);
            return stateIn.with(ON_SOLID_GROUND, stateIsSolid);
        }
        return stateIn;
    }

    @Override
    public FluidState getFluidState(BlockState state)
    {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state)
    {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }
}
