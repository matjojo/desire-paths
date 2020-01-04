//Copyright (C) 2019  Matjojo
//
//        This program is free software: you can redistribute it and/or modify
//        it under the terms of the GNU General Public License as published by
//        the Free Software Foundation, either version 3 of the License, or
//        (at your option) any later version.
//
//        This program is distributed in the hope that it will be useful,
//        but WITHOUT ANY WARRANTY; without even the implied warranty of
//        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//        GNU General Public License for more details.
//
//        You should have received a copy of the GNU General Public License
//        along with this program.  If not, see <https://www.gnu.org/licenses/>.

package com.matjojo.desire_paths.data.Blocks;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.matjojo.desire_paths.core.TrampleUtil;
import com.matjojo.desire_paths.core.Util;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;

import java.util.Map;
import java.util.Random;

public class Trampleable extends GrassBlock implements Fertilizable {

    private DesirePathBlock blockType;
    private static Map ItemStackMap;
    private static Map PreviousBlockMap;

    private Trampleable(Settings blockSettings) {
        super(blockSettings.dropsLike(Blocks.DIRT));
    }

    Trampleable(Settings blockSettings, DesirePathBlock whatBlock) {
        this(blockSettings);
        this.blockType = whatBlock;
    }

    static {
        ItemStackMap = Maps.newHashMap(ImmutableMap.of(
                DesirePathBlock.DIRT_COARSE_INTER,      new ItemStack(Blocks.DIRT),
                DesirePathBlock.GRASS_DIRT_INTER,       new ItemStack(Blocks.GRASS_BLOCK),
                DesirePathBlock.MYCELIUM_DIRT_INTER,    new ItemStack(Blocks.MYCELIUM),
                DesirePathBlock.PODZOL_DIRT_INTER,      new ItemStack(Blocks.PODZOL)
        ));

        PreviousBlockMap = Maps.newHashMap(ImmutableMap.of(
                DesirePathBlock.DIRT_COARSE_INTER,      Blocks.DIRT,
                DesirePathBlock.GRASS_DIRT_INTER,       Blocks.GRASS_BLOCK,
                DesirePathBlock.MYCELIUM_DIRT_INTER,    Blocks.MYCELIUM,
                DesirePathBlock.PODZOL_DIRT_INTER,      Blocks.PODZOL
        ));
    }

    // TODO: we could use an enum and a map to make all those if else trees in util easier.

    public Block getPreviousBlock() {
        Block returnable = (Block) PreviousBlockMap.get(this.blockType);
        if (returnable == null) {
            returnable = Blocks.DIRT;
        }
        return returnable;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateFactory) {
        super.appendProperties(stateFactory);
        stateFactory.add(Util.DESIRE_PATH_PROPERTY);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos position, Random random) {
        TrampleUtil.triggerUnTrample(state, world, position);
    }

    @Override
    public ItemStack getPickStack(BlockView view, BlockPos position, BlockState state) {
        ItemStack returnable = (ItemStack) Trampleable.ItemStackMap.get(this.blockType);
        if (returnable == null) {
            returnable = new ItemStack(Blocks.DIRT);
        }
        return returnable;
    }

    public static boolean isNextToValidWater(WorldView world, BlockPos position) {
        for (Direction offset : Direction.Type.HORIZONTAL) {
            BlockState blockNextTo = world.getBlockState(position.offset(offset));
            FluidState fluidNextTo = world.getFluidState(position.offset(offset));
            if (fluidNextTo.matches(FluidTags.WATER) || blockNextTo.getBlock() == Blocks.FROSTED_ICE) {
                return true;
            }
        }
        return false;
    }
}
