package com.hidoni.additionalenderitems.blocks;

import com.hidoni.additionalenderitems.network.Networking;
import com.hidoni.additionalenderitems.network.PacketStopJukebox;
import com.hidoni.additionalenderitems.tileentities.EnderJukeboxTileEntity;
import com.hidoni.additionalenderitems.util.MusicDiscPlayingUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class EnderJukeboxBlock extends ContainerBlock
{
    public static final BooleanProperty HAS_RECORD = BlockStateProperties.HAS_RECORD;

    public EnderJukeboxBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(HAS_RECORD, Boolean.FALSE));
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        if (state.get(HAS_RECORD))
        {
            this.dropRecord(worldIn, pos);
            state = state.with(HAS_RECORD, Boolean.FALSE);
            worldIn.setBlockState(pos, state, 2);
            return ActionResultType.func_233537_a_(worldIn.isRemote);
        }
        else
        {
            ItemStack currentItem = player.inventory.getCurrentItem();
            if (currentItem.getItem() instanceof MusicDiscItem) // Right clicked with a Music Disc
            {
                insertRecord(worldIn, pos, state, currentItem);
                if (worldIn.isRemote())
                {
                    MusicDiscPlayingUtil.playEvent(pos, Item.getIdFromItem(currentItem.getItem()));
                }
                if (!player.isCreative())
                {
                    currentItem.shrink(1);
                }
                player.addStat(Stats.PLAY_RECORD);
                return ActionResultType.CONSUME;
            }
            return ActionResultType.PASS;
        }
    }

    public void insertRecord(IWorld worldIn, BlockPos pos, BlockState state, ItemStack recordStack)
    {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        if (tileentity instanceof EnderJukeboxTileEntity)
        {
            ((EnderJukeboxTileEntity) tileentity).setRecord(recordStack.copy());
            worldIn.setBlockState(pos, state.with(HAS_RECORD, Boolean.TRUE), 2);
        }
    }

    public void dropRecord(World worldIn, BlockPos pos)
    {
        if (!worldIn.isRemote)
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof EnderJukeboxTileEntity)
            {
                EnderJukeboxTileEntity jukeboxtileentity = (EnderJukeboxTileEntity) tileentity;
                ItemStack itemstack = jukeboxtileentity.getRecord();
                if (!itemstack.isEmpty())
                {
                    worldIn.playEvent(1010, pos, 0);
                    jukeboxtileentity.clear();
                    float f = 0.7F;
                    double d0 = (double) (worldIn.rand.nextFloat() * 0.7F) + (double) 0.15F;
                    double d1 = (double) (worldIn.rand.nextFloat() * 0.7F) + (double) 0.060000002F + 0.6D;
                    double d2 = (double) (worldIn.rand.nextFloat() * 0.7F) + (double) 0.15F;
                    ItemStack itemstack1 = itemstack.copy();
                    ItemEntity itementity = new ItemEntity(worldIn, (double) pos.getX() + d0, (double) pos.getY() + d1, (double) pos.getZ() + d2, itemstack1);
                    itementity.setDefaultPickupDelay();
                    worldIn.addEntity(itementity);
                }
            }
        }
        else
        {
            MusicDiscPlayingUtil.playEvent(pos, 0);
        }
    }

    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving)
    {
        if (!state.isIn(newState.getBlock()))
        {
            this.dropRecord(worldIn, pos);
            Networking.sendAll(new PacketStopJukebox(pos));
            super.onReplaced(state, worldIn, pos, newState, isMoving);
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(HAS_RECORD);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state)
    {
        return BlockRenderType.MODEL;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack)
    {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        CompoundNBT compoundnbt = stack.getOrCreateTag();
        if (compoundnbt.contains("BlockEntityTag"))
        {
            CompoundNBT compoundnbt1 = compoundnbt.getCompound("BlockEntityTag");
            if (compoundnbt1.contains("RecordItem"))
            {
                worldIn.setBlockState(pos, state.with(HAS_RECORD, Boolean.TRUE), 2);
            }
        }

    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn)
    {
        return new EnderJukeboxTileEntity();
    }
}
