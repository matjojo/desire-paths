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

import com.matjojo.desire_paths.data.DesirePathsDataHolder;
import net.minecraft.block.*;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateFactory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public class Trampleable extends GrassBlock implements Fertilizable {
    public Trampleable(Settings block$Settings_1) {
        super(block$Settings_1.dropsLike(Blocks.DIRT));
    }

    @Override
    protected void appendProperties(StateFactory.Builder<Block, BlockState> stateFactory$Builder_1) {
        super.appendProperties(stateFactory$Builder_1);
        stateFactory$Builder_1.add(DesirePathsDataHolder.DESIRE_PATH_PROPERTY);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onRandomTick(BlockState blockState_1, World world_1, BlockPos blockPos_1, Random random_1) {
        DesirePathsDataHolder.triggerUnTrample(blockState_1, world_1, blockPos_1);
    }

    @Override
    public ItemStack getPickStack(BlockView blockView_1, BlockPos blockPos_1, BlockState blockState_1) {
        return new ItemStack(Blocks.DIRT);
    }
    
}
