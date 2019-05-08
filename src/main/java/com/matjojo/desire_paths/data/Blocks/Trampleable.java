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
import net.minecraft.state.StateFactory;

public class Trampleable extends GrassBlock implements Fertilizable {
    public Trampleable(Settings block$Settings_1) {
        super(block$Settings_1.dropsLike(Blocks.DIRT));
    }

    @Override
    protected void appendProperties(StateFactory.Builder<Block, BlockState> stateFactory$Builder_1) {
        super.appendProperties(stateFactory$Builder_1);
        stateFactory$Builder_1.add(DesirePathsDataHolder.DESIRE_PATH_PROPERTY);
    }
}
