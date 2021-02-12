package com.hidoni.additionalenderitems.blocks;

import com.hidoni.additionalenderitems.AdditionalEnderItems;
import com.hidoni.additionalenderitems.config.BlockConfig;
import com.hidoni.additionalenderitems.setup.ModSoundEvents;
import com.hidoni.additionalenderitems.tileentities.WarpPortalTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SPlaySoundEventPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class WarpPortalBlock extends Block
{
    private static final int MAX_CHARGES = 8;
    public static final IntegerProperty CHARGES = IntegerProperty.create("charges", 0, MAX_CHARGES);
    ;
    private static final Map<Item, Integer> VALID_ITEMS = new LinkedHashMap<Item, Integer>()
    {{
        put(Items.ENDER_PEARL, BlockConfig.warpPortalPearlFuelValue.get());
        put(Items.ENDER_EYE, BlockConfig.warpPortalEyeFuelValue.get());
    }};
    public static final String PORTAL_DATA_NBT_KEY = AdditionalEnderItems.MOD_ID + ":warpportallocation";
    public static final String LOCAL_WARP_PORTAL_NO_CHARGE_MESSAGE = "block.additionalenderitems.local_warp_portal_no_charge";
    public static final String END_WARP_PORTAL_NO_CHARGE_MESSAGE = "block.additionalenderitems.end_warp_portal_no_charge";
    public static final String END_WARP_PORTAL_MISSING = "block.additionalenderitems.missing_warp_portal";
    public static final String END_WARP_LOCATION_SET = "block.additionalenderitems.warp_location_set";
    protected static final VoxelShape BASE_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 13.0D, 16.0D);

    public WarpPortalBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(CHARGES, 0));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(CHARGES);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        ItemStack itemIn = player.getHeldItem(handIn);
        if (handIn == Hand.MAIN_HAND && !isValidFuel(itemIn) && isValidFuel(player.getHeldItem(Hand.OFF_HAND)))
        {
            return ActionResultType.PASS;
        }
        else if (isValidFuel(itemIn) && !isFullyCharged(state)) // Dual-side functionality, charging the portal.
        {
            fuelPortal(worldIn, pos, state, itemIn);
            if (!player.isCreative())
            {
                itemIn.shrink(1);
            }
            return ActionResultType.func_233537_a_(worldIn.isRemote);
        }
        BlockPos warpPos = getPlayerWarpPosition(player);
        if (worldIn.getDimensionKey() == World.THE_END) // End-side functionality, setting warp location.
        {
            if (warpPos != null && !warpPos.equals(pos))
            {
                setPlayerWarpPosition(player, pos);
                player.sendMessage(new TranslationTextComponent(END_WARP_LOCATION_SET), Util.DUMMY_UUID);
                return ActionResultType.func_233537_a_(worldIn.isRemote);
            }
            return ActionResultType.PASS;
        }
        if (!worldIn.isRemote && warpPos != null) // Other worlds functionality, warping to set location.
        {
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) player;
            ServerWorld endWorld = serverPlayerEntity.getServer().getWorld(World.THE_END);
            IChunk endWorldChunk = endWorld.getChunk(warpPos);
            BlockState endWorldBlock = endWorldChunk.getBlockState(warpPos);
            if (endWorldBlock.getBlock() instanceof WarpPortalBlock)
            {
                if (!isEmpty(state) && !isEmpty(endWorldBlock))
                {
                    updateChargeAmount(worldIn, pos, state, state.get(CHARGES) - 1);
                    updateChargeAmount(endWorld, warpPos, endWorldBlock, state.get(CHARGES) - 1);
                    serverPlayerEntity.teleport(endWorld, warpPos.getX() + 0.5D, warpPos.getY() + 1, warpPos.getZ() + 0.5D, serverPlayerEntity.rotationYaw, serverPlayerEntity.rotationPitch);
                    serverPlayerEntity.connection.sendPacket(new SPlaySoundEventPacket(1032, pos, 0, false));
                }
                else if (isEmpty(state))
                {
                    player.sendMessage(new TranslationTextComponent(LOCAL_WARP_PORTAL_NO_CHARGE_MESSAGE), Util.DUMMY_UUID);
                }
                else
                {
                    player.sendMessage(new TranslationTextComponent(END_WARP_PORTAL_NO_CHARGE_MESSAGE), Util.DUMMY_UUID);
                }
            }
            else
            {
                player.sendMessage(new TranslationTextComponent(END_WARP_PORTAL_MISSING), Util.DUMMY_UUID);
            }
        }
        return ActionResultType.func_233537_a_(worldIn.isRemote);
    }

    public BlockPos getPlayerWarpPosition(PlayerEntity player)
    {
        CompoundNBT playerNBT = getPersistentPlayerData(player);
        if (playerNBT.contains(PORTAL_DATA_NBT_KEY))
        {
            CompoundNBT warpPositionNBT = playerNBT.getCompound(PORTAL_DATA_NBT_KEY);
            return new BlockPos(warpPositionNBT.getInt("x"), warpPositionNBT.getInt("y"), warpPositionNBT.getInt("z"));
        }
        return null;
    }

    private void setPlayerWarpPosition(PlayerEntity player, BlockPos pos)
    {
        CompoundNBT playerNBT = getPersistentPlayerData(player);
        CompoundNBT warpPositionNBT = new CompoundNBT();
        warpPositionNBT.putInt("x", pos.getX());
        warpPositionNBT.putInt("y", pos.getY());
        warpPositionNBT.putInt("z", pos.getZ());
        playerNBT.put(PORTAL_DATA_NBT_KEY, warpPositionNBT);
        player.getPersistentData().put(player.PERSISTED_NBT_TAG, playerNBT);
    }

    private CompoundNBT getPersistentPlayerData(PlayerEntity player)
    {
        CompoundNBT persistentData = player.getPersistentData();
        if (!persistentData.contains(player.PERSISTED_NBT_TAG))
        {
            persistentData.put(player.PERSISTED_NBT_TAG, new CompoundNBT());
        }
        return persistentData.getCompound(player.PERSISTED_NBT_TAG);
    }

    public boolean isFullyCharged(BlockState state)
    {
        return state.get(CHARGES) == MAX_CHARGES;
    }

    public boolean isEmpty(BlockState state)
    {
        return state.get(CHARGES) == 0;
    }

    public boolean isValidFuel(ItemStack itemIn)
    {
        return VALID_ITEMS.containsKey(itemIn.getItem());
    }

    public Integer getFuelValue(ItemStack itemIn)
    {
        if (isValidFuel(itemIn))
        {
            return VALID_ITEMS.get(itemIn.getItem());
        }
        return 0;
    }

    public void fuelPortal(World world, BlockPos pos, BlockState state, ItemStack fuel)
    {
        updateChargeAmount(world, pos, state, Math.min(state.get(CHARGES) + getFuelValue(fuel), MAX_CHARGES));
        world.playSound(null, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, ModSoundEvents.WARP_PORTAL_CHARGE.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
    }

    private void updateChargeAmount(World world, BlockPos pos, BlockState state, int newAmount)
    {
        world.setBlockState(pos, state.with(CHARGES, newAmount));
    }

    public static int getEmittedLightLevel(BlockState state)
    {
        int charges = state.get(CHARGES);
        if (charges == 0)
        {
            return 1;
        }
        if (charges < 4)
        {
            return 3;
        }
        if (charges < 8)
        {
            return 5;
        }
        return 7;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        if (!isEmpty(stateIn) && (rand.nextInt(MAX_CHARGES) - stateIn.get(CHARGES)) <= 0)
        {
            double d0 = (double) pos.getX() + rand.nextDouble();
            double d1 = (double) pos.getY() + 0.8D;
            double d2 = (double) pos.getZ() + rand.nextDouble();
            worldIn.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        return BASE_SHAPE;
    }

    @Override
    public boolean isTransparent(BlockState state)
    {
        return true;
    }

    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return !isEmpty(state);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new WarpPortalTileEntity();
    }
}
