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
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.state.property.IntegerProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class DesirePathsDataHolder {
    public static final IntegerProperty DESIRE_PATH_PROPERTY;
    public static final int speedThreshold;
    private static final int MAX_TRAMPLE;
    private static final int UNTRAMPLE_PER_RANDOM_TICK;

    static {
        speedThreshold = 20; // Player moves 6, 22, 28 when crouching, walking, running.
        MAX_TRAMPLE = 5 * 5; // the amount of ticks that you'd have to walk on this block
        // since you take, when walking, 100/22 = 4.5 ticks per block,
        // we'd want you to walk over the block about 5 times before getting a desire path
        DESIRE_PATH_PROPERTY = IntegerProperty.create("desiretramples", 0, DesirePathsDataHolder.MAX_TRAMPLE);
        UNTRAMPLE_PER_RANDOM_TICK = 5;
    }

    private static Block getNextBlock(Block currentBlock) {
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

    private static boolean isBlockToChange(BlockState toCheck) {
        return toCheck.getBlock().equals(Blocks.DIRT) ||
                toCheck.getBlock().equals(Blocks.GRASS_BLOCK) ||
                toCheck.getBlock().equals(Blocks.PODZOL) ||
                toCheck.getBlock().equals(Blocks.MYCELIUM);
    }

    private static boolean isBlockToTrample(BlockState toCheck) {
        return toCheck.getProperties().contains(DesirePathsDataHolder.DESIRE_PATH_PROPERTY);
    }

    public static void triggerTrample(PlayerEntity player) {
        // search for the block below the player
        BlockPos blockPositionBelowPlayer = new BlockPos(MathHelper.floor(player.x), MathHelper.floor(player.y) - 1, MathHelper.floor(player.z));
        BlockState blockStateBelowPlayer = player.world.getBlockState(blockPositionBelowPlayer);

        if (DesirePathsDataHolder.isBlockToTrample(blockStateBelowPlayer)) {
            int currentTrampleValue = blockStateBelowPlayer.get(DesirePathsDataHolder.DESIRE_PATH_PROPERTY);
            if (!(currentTrampleValue == DesirePathsDataHolder.MAX_TRAMPLE)) {
                player.world.setBlockState(
                        blockPositionBelowPlayer,
                        blockStateBelowPlayer.with(DesirePathsDataHolder.DESIRE_PATH_PROPERTY, currentTrampleValue + 1),
                        DesirePathsDataHolder.MAX_TRAMPLE);
            } else {// we set the block to the next level
                player.world.setBlockState(blockPositionBelowPlayer, DesirePathsDataHolder.getNextBlock(blockStateBelowPlayer.getBlock()).getDefaultState());
            }
        } else if (DesirePathsDataHolder.isBlockToChange(blockStateBelowPlayer)) {
            player.world.setBlockState(blockPositionBelowPlayer, DesirePathsDataHolder.getNextBlock(blockStateBelowPlayer.getBlock()).getDefaultState());

        }
    }

    private static Block getPreviousBlock(Block block, World world) {
        if (block.equals(DesirePathInitializer.GRASS_DIRT_INTER)) {
            return Blocks.GRASS_BLOCK;
        } else if (block.equals(DesirePathInitializer.DIRT_COARSE_INTER)) {
            return Blocks.DIRT;
        } else if (block.equals(DesirePathInitializer.MYCELIUM_DIRT_INTER)) {
            return Blocks.MYCELIUM;
        } else if (block.equals(DesirePathInitializer.PODZOL_DIRT_INTER)) {
            return Blocks.PODZOL;
        }

        MinecraftServer server = world.getServer();

        if (server != null) {
            Throwable traceProvider = new Throwable();
            server.sendMessage(new TextComponent("An error occurred in the DesirePaths mod. Please report this to the mod creator."));
            server.sendMessage(new TextComponent("Please give this error log to the mod creator, it is also in the log."));
            server.sendMessage(new TextComponent(traceProvider.getLocalizedMessage()));
            traceProvider.printStackTrace();
        }
        return Blocks.DIRT;
    }

    public static void triggerUnTrample(BlockState state, World world, BlockPos position) {
        for (int i = 0; i < DesirePathsDataHolder.UNTRAMPLE_PER_RANDOM_TICK; i++) {
            DesirePathsDataHolder.unTrample(state, world, position);
        }
    }

    private static void unTrample(BlockState state, World world, BlockPos position) {
        int currentTrampleValue = state.get(DesirePathsDataHolder.DESIRE_PATH_PROPERTY);
        BlockState newBlockState;
        if (currentTrampleValue == 0) {
            //get previous block make sure to set the trample value to the max
            Block previousBlock = DesirePathsDataHolder.getPreviousBlock(state.getBlock(), world);

            if (previousBlock.getDefaultState().getProperties().contains(DesirePathsDataHolder.DESIRE_PATH_PROPERTY)) {
                newBlockState = previousBlock.getDefaultState().with(
                        DesirePathsDataHolder.DESIRE_PATH_PROPERTY,
                        DesirePathsDataHolder.MAX_TRAMPLE);
            } else {
                newBlockState = previousBlock.getDefaultState();
            }

        } else { // decrease the trample value
            newBlockState = state.with(DesirePathsDataHolder.DESIRE_PATH_PROPERTY, currentTrampleValue - 1);
        }

        world.setBlockState(position, newBlockState);
    }
}
