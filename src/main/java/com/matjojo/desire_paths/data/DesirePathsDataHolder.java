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
//
package com.matjojo.desire_paths.data;


import com.matjojo.desire_paths.init.DesirePathInitializer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.property.IntegerProperty;

public class DesirePathsDataHolder {
    public static final int MAX_TRAMPLE;
    public static final IntegerProperty DESIRE_PATH_PROPERTY;
    private static final int speedThreshold;

    static {
        speedThreshold = 10; // Player moves 6, 22, 28 when crouching, walking, running.
        MAX_TRAMPLE = 5 * 5; // the amount of ticks that you'd have to walk on this block
        // since you take, when walking, 100/22 = 4.5 ticks per block,
        // we'd want you to walk over the block about 5 times before getting a desire path
        DESIRE_PATH_PROPERTY = IntegerProperty.create("desiretramples", 0, DesirePathsDataHolder.MAX_TRAMPLE);
    }

    public static Block getNextBlock(Block currentBlock) {
        if (Blocks.DIRT.equals(currentBlock)) {
            return DesirePathInitializer.DIRT_COARSE_INTER;
        } else if (DesirePathInitializer.DIRT_COARSE_INTER.equals(currentBlock)) {
            return Blocks.COARSE_DIRT;
        } else if (Blocks.GRASS_BLOCK.equals(currentBlock)) {
            return DesirePathInitializer.GRASS_DIRT_INTER;
        } else if (Blocks.MYCELIUM.equals(currentBlock)) {
            return DesirePathInitializer.MYCELIUM_DIRT_INTER;
        } else if (Blocks.PODZOL.equals(currentBlock)) {
            return DesirePathInitializer.PODZOL_DIRT_INTER;
        } // all non dirt_coarse_inter inters turn into dirt
        return Blocks.DIRT;
    }

    public static boolean isBlockToChange(BlockState toCheck) {
        return toCheck.getBlock().equals(Blocks.DIRT) ||
                toCheck.getBlock().equals(Blocks.GRASS_BLOCK) ||
                toCheck.getBlock().equals(Blocks.PODZOL) ||
                toCheck.getBlock().equals(Blocks.MYCELIUM);
    }


    public static boolean isBlockToTrample(BlockState toCheck) {
        return toCheck.getProperties().contains(DesirePathsDataHolder.DESIRE_PATH_PROPERTY);
    }

    public static boolean playerIsTrampling(PlayerEntity player, int distance) {
        return distance > DesirePathsDataHolder.speedThreshold &&
                player.abilities.allowModifyWorld &&
                !player.hasVehicle() &&
                player.onGround &&
                !player.isSneaking() &&
                !player.isInsideWater();
    }

}
