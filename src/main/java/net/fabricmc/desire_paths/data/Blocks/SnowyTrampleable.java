package net.fabricmc.desire_paths.data.Blocks;

import net.fabricmc.desire_paths.data.DesirePathsDataHolder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SnowyBlock;
import net.minecraft.state.StateFactory;

public class SnowyTrampleable extends SnowyBlock {
    public SnowyTrampleable(Settings block$Settings_1) {
        super(block$Settings_1);
    }

    protected void appendProperties(StateFactory.Builder<Block, BlockState> stateBuilder) {
        super.appendProperties(stateBuilder);
        stateBuilder.with(DesirePathsDataHolder.DESIRE_PATH_PROPERTY);
    }
}
