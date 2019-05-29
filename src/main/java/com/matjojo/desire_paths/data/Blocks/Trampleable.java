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

import com.matjojo.desire_paths.core.TrampleUtil;
import com.matjojo.desire_paths.core.Util;
import net.minecraft.block.*;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateFactory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public class Trampleable extends GrassBlock implements Fertilizable {

    private String blockName;

    Trampleable(Settings blockSettings) {
        super(blockSettings.dropsLike(Blocks.DIRT));
    }

    Trampleable(Settings blockSettings, String whatBlock) {
        this(blockSettings);
        this.blockName = whatBlock;
    }

    // TODO: we could use an enum and a map to make all those if else trees in util easier.

    @Override
    protected void appendProperties(StateFactory.Builder<Block, BlockState> stateFactory) {
        super.appendProperties(stateFactory);
        stateFactory.add(Util.DESIRE_PATH_PROPERTY);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onRandomTick(BlockState state, World world, BlockPos position, Random random) {
        TrampleUtil.triggerUnTrample(state, world, position);
    }

    @Override
    public ItemStack getPickStack(BlockView view, BlockPos position, BlockState state) {
        return Util.getItemStackForTrampleableBlock(state.getBlock());
    }

}
