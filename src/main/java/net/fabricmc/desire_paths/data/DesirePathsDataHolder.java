package net.fabricmc.desire_paths.data;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.property.IntegerProperty;

public class DesirePathsDataHolder {
    public static final int MAX_TRAMPLE;
    public static final IntegerProperty DESIRE_PATH_PROPERTY;
    private static final int speedThreshold;
    private static final String[] BLOCK_NAMES_GENERIC_TO_CHANGE;
    private static final String[] BLOCK_NAMES_SNOWY_TO_CHANGE;

    static {
        speedThreshold = 10; // Player moves 6, 22, 28 when crouching, walking, running.
        MAX_TRAMPLE = 5 * 5; // the amount of ticks that you'd have to walk on this block
        // since you take, when walking, 100/22 = 4.5 ticks per block,
        // we'd want you to walk over the block about 5 times before getting a desire path
        DESIRE_PATH_PROPERTY = IntegerProperty.create("desiretramples", 0, DesirePathsDataHolder.MAX_TRAMPLE);
        BLOCK_NAMES_GENERIC_TO_CHANGE = new String[]{
                "dirt",
                "coarse_dirt"
        };
        BLOCK_NAMES_SNOWY_TO_CHANGE = new String[]{
                "podzol"
        };
    }


    public static boolean isBlockToChange(BlockState toCheck) {
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

    private static boolean shouldInjectTrampleableBlock(String blockName, String[] blockList) {
        for (String changeable : blockList) {
            if (blockName.equals(changeable)) {
                return true;
            }
        }
        return false;
    }

    public static boolean shouldInjectGenericTrampleableBlock(String blockName) {
        return shouldInjectTrampleableBlock(blockName, BLOCK_NAMES_GENERIC_TO_CHANGE);
    }

    public static boolean shouldInjectSnowyTrampleableBlock(String blockName) {
        return shouldInjectTrampleableBlock(blockName, BLOCK_NAMES_SNOWY_TO_CHANGE);
    }

}
