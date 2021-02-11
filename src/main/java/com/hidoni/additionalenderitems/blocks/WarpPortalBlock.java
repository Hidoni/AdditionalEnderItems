package com.hidoni.additionalenderitems.blocks;

import com.hidoni.additionalenderitems.config.BlockConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

import java.util.LinkedHashMap;
import java.util.Map;

public class WarpPortalBlock extends Block
{
    public static final IntegerProperty CHARGES = BlockStateProperties.CHARGES;
    private static final Map<Item, Integer> validItems = new LinkedHashMap<Item, Integer>()
    {{
        put(Items.ENDER_PEARL, BlockConfig.warpPortalPearlFuelValue.get());
        put(Items.ENDER_EYE, BlockConfig.warpPortalEyeFuelValue.get());
    }};

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
        else if (isValidFuel(itemIn))
        {

        }
    }

    public boolean

    public boolean isValidFuel(ItemStack itemIn)
    {
        return validItems.containsKey(itemIn.getItem());
    }

    public Integer getFuelValue(ItemStack itemIn)
    {
        if (isValidFuel(itemIn))
        {
            return validItems.get(itemIn.getItem());
        }
        return 0;
    }
}
