package com.matjojo.desire_paths.core;

import com.matjojo.desire_paths.data.Blocks.DesirePathBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class TrampleUtil {


    static final int MAX_TRAMPLE;
    private static final int speedThreshold;
    private static final int UNTRAMPLE_PER_RANDOM_TICK;

    static {
        speedThreshold = 20; // Player moves 6, 22, 28 when crouching, walking, running.
        MAX_TRAMPLE = 5 * 5; // the amount of ticks that you'd have to walk on this block
        // since you take, when walking, 100/22 = 4.5 ticks per block,
        // we'd want you to walk over the block about 5 times before going to the next stage
        UNTRAMPLE_PER_RANDOM_TICK = 2;
    }


    private static Block getNextBlock(Block currentBlock) {
        if (Blocks.DIRT.equals(currentBlock)) {
            return DesirePathBlocks.DIRT_COARSE_INTER;
        } else if (DesirePathBlocks.DIRT_COARSE_INTER.equals(currentBlock)) {
            return Blocks.COARSE_DIRT;
        } else if (Blocks.GRASS_BLOCK.equals(currentBlock)) {
            return DesirePathBlocks.GRASS_DIRT_INTER;
        } else if (Blocks.MYCELIUM.equals(currentBlock)) {
            return DesirePathBlocks.MYCELIUM_DIRT_INTER;
        } else if (Blocks.PODZOL.equals(currentBlock)) {
            return DesirePathBlocks.PODZOL_DIRT_INTER;
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
        return toCheck.getProperties().contains(Util.DESIRE_PATH_PROPERTY);
    }

    public static boolean playerIsTrampling(PlayerEntity player, int distance) {
        return distance > TrampleUtil.speedThreshold &&
                player.abilities.allowModifyWorld &&
                !player.hasVehicle() &&
                player.onGround &&
                !player.isSneaking() &&
                !player.isInsideWater();
    }

    public static void triggerTrample(PlayerEntity player) {
        // search for the block below the player
        BlockPos blockPositionBelowPlayer = new BlockPos(MathHelper.floor(player.x), MathHelper.floor(player.y) - 1, MathHelper.floor(player.z));
        BlockState blockStateBelowPlayer = player.world.getBlockState(blockPositionBelowPlayer);

        if (TrampleUtil.isBlockToTrample(blockStateBelowPlayer)) {
            int currentTrampleValue = blockStateBelowPlayer.get(Util.DESIRE_PATH_PROPERTY);
            if (!(currentTrampleValue == TrampleUtil.MAX_TRAMPLE)) {
                player.world.setBlockState(
                        blockPositionBelowPlayer,
                        blockStateBelowPlayer.with(Util.DESIRE_PATH_PROPERTY, currentTrampleValue + 1),
                        TrampleUtil.MAX_TRAMPLE);
            } else {// we set the block to the next level
                player.world.setBlockState(blockPositionBelowPlayer, TrampleUtil.getNextBlock(blockStateBelowPlayer.getBlock()).getDefaultState());
            }
        } else if (TrampleUtil.isBlockToChange(blockStateBelowPlayer)) {
            player.world.setBlockState(blockPositionBelowPlayer, TrampleUtil.getNextBlock(blockStateBelowPlayer.getBlock()).getDefaultState());

        }
    }

    private static Block getPreviousBlock(Block block, World world) {
        if (block.equals(DesirePathBlocks.GRASS_DIRT_INTER)) {
            return Blocks.GRASS_BLOCK;
        } else if (block.equals(DesirePathBlocks.DIRT_COARSE_INTER)) {
            return Blocks.DIRT;
        } else if (block.equals(DesirePathBlocks.MYCELIUM_DIRT_INTER)) {
            return Blocks.MYCELIUM;
        } else if (block.equals(DesirePathBlocks.PODZOL_DIRT_INTER)) {
            return Blocks.PODZOL;
        } else if (block.equals(Blocks.DIRT)) {
            return Blocks.DIRT;
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
        for (int i = 0; i < TrampleUtil.UNTRAMPLE_PER_RANDOM_TICK; i++) {
            TrampleUtil.unTrample(state, world, position);
        }
    }

    private static void unTrample(BlockState state, World world, BlockPos position) {
        int currentTrampleValue = state.get(Util.DESIRE_PATH_PROPERTY);
        BlockState newBlockState;
        if (currentTrampleValue == 0) {
            //get previous block make sure to set the trample value to the max
            Block previousBlock = TrampleUtil.getPreviousBlock(state.getBlock(), world);

            if (previousBlock.getDefaultState().getProperties().contains(Util.DESIRE_PATH_PROPERTY)) {
                newBlockState = previousBlock.getDefaultState().with(
                        Util.DESIRE_PATH_PROPERTY,
                        TrampleUtil.MAX_TRAMPLE);
            } else {
                newBlockState = previousBlock.getDefaultState();
            }

        } else { // decrease the trample value
            newBlockState = state.with(Util.DESIRE_PATH_PROPERTY, currentTrampleValue - 1);
        }

        world.setBlockState(position, newBlockState);
    }
}
