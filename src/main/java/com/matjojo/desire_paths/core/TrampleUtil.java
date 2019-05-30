package com.matjojo.desire_paths.core;

import com.matjojo.desire_paths.config.DesirePathConfig;
import com.matjojo.desire_paths.data.Blocks.DesirePathBlocks;
import com.matjojo.desire_paths.data.Blocks.Trampleable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class TrampleUtil {

    static final int MAX_TRAMPLE;
    private static final int DISTANCE_MINIMUM;
    private static final int UNTRAMPLE_ATTEMPTS_PER_RANDOM_TICK;

    static {
        DISTANCE_MINIMUM = 20; // Player moves 6, 22, 28 when crouching, walking, running.
        MAX_TRAMPLE = 5; // the amount of states that there are for the blocks
        UNTRAMPLE_ATTEMPTS_PER_RANDOM_TICK = 2;
    }

    /**
     * @param currentBlock The <code>Block</code> that you want the 'next' trampled state of
     * @return The <code>Block</code> that you want to get the 'next' <code>Block</code> of
     */
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

    /**
     * Checks whether or not the given <code>BlockState</code> needs to be changed into an intermediate or not
     *
     * @param toCheck The <code>BlockState</code> that you want to know for
     * @return A <code>boolean</code> that says if this block is one to change into an intermediate block
     */
    private static boolean isBlockToChange(BlockState toCheck) {
        Block toCheckBlock = toCheck.getBlock();
        return  toCheckBlock.equals(Blocks.DIRT) ||
                toCheckBlock.equals(Blocks.GRASS_BLOCK) ||
                toCheckBlock.equals(Blocks.PODZOL) ||
                toCheckBlock.equals(Blocks.MYCELIUM);
    }

    /**
     * @param toCheck <code>BlockState</code> that you might want to trample
     * @return <code>boolean</code>, true if block contains the trample property, and thus can be trampled
     */
    private static boolean isBlockToTrample(BlockState toCheck) {
        return toCheck.getProperties().contains(Util.DESIRE_PATH_PROPERTY);
    }

    public static boolean playerIsTrampling(PlayerEntity player, int distance) {
        return distance > TrampleUtil.DISTANCE_MINIMUM &&
                player.abilities.allowModifyWorld &&
                !player.hasVehicle() &&
                player.onGround &&
                !player.isSneaking() &&
                !player.isInsideWater();
    }

    public static void triggerTrample(PlayerEntity player) {
        BlockPos blockPositionBelowPlayer = new BlockPos(MathHelper.floor(player.x), MathHelper.floor(player.y) - 1, MathHelper.floor(player.z));
        BlockState blockStateBelowPlayer = player.world.getBlockState(blockPositionBelowPlayer);

        BlockState nextState = TrampleUtil.getNextTrampleableBlockState(blockStateBelowPlayer);
        if (nextState != null &&
            player.getRand().nextDouble() < DesirePathConfig.TRAMPLE_CHANCE ) {
            player.world.setBlockState(blockPositionBelowPlayer, nextState);
        }
    }

    /**
     * @param currentState The <code>BlockState</code> that you want the next 'trampled' <code>BlockState</code> for
     * @return <code>null</code> if the <code>BlockState</code> is not a trampleable, and otherwise the 'next' <code>BlockState</code>
     */
    private static BlockState getNextTrampleableBlockState(BlockState currentState) {
        if (TrampleUtil.isBlockToChange(currentState)) {
            // if the block is one of the 'base' states
            return TrampleUtil.getNextBlock(currentState.getBlock()).getDefaultState();
        } else if (TrampleUtil.isBlockToTrample(currentState)) {
            int currentTrampleAmount = currentState.get(Util.DESIRE_PATH_PROPERTY);
            if (currentTrampleAmount == TrampleUtil.MAX_TRAMPLE) {
                // if the block needs to be changed to the next block
                return TrampleUtil.getNextBlock(currentState.getBlock()).getDefaultState();
            } else { // we contain the property but it is not at it's max
                return currentState.with(Util.DESIRE_PATH_PROPERTY, currentTrampleAmount + 1);
            }
        } // This is not a block of ours

        return null;
    }

    public static void triggerUnTrample(BlockState state, World world, BlockPos position) {
        for (int i = 0; i < TrampleUtil.UNTRAMPLE_ATTEMPTS_PER_RANDOM_TICK; i++) {
            if (world.random.nextDouble() <= DesirePathConfig.TRAMPLE_CHANCE) {
                TrampleUtil.unTrample(state, world, position);
            }
        }
    }

    private static void unTrample(BlockState state, World world, BlockPos position) {
        int currentTrampleValue = state.get(Util.DESIRE_PATH_PROPERTY);
        BlockState newBlockState;
        if (currentTrampleValue == 0) {
            //get previous block make sure to set the trample value to the max
            Block previousBlock = ((Trampleable)state.getBlock()).getPreviousBlock();

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
