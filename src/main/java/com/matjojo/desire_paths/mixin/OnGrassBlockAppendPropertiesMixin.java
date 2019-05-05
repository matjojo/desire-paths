package com.matjojo.desire_paths.mixin;


import com.matjojo.desire_paths.data.DesirePathsDataHolder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SpreadableBlock;
import net.minecraft.state.StateFactory;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(net.minecraft.block.GrassBlock.class)
public abstract class OnGrassBlockAppendPropertiesMixin extends SpreadableBlock {

    public OnGrassBlockAppendPropertiesMixin(Block.Settings block$Settings_1) {
        super(block$Settings_1);
    }

    public void appendProperties(StateFactory.Builder<Block, BlockState> stateFactory$Builder_1) {
        super.appendProperties(stateFactory$Builder_1);
        // first we call the super to make sure all the supertypes get a change to init, eg: snowyBlock
        // after that we add our own property.
        stateFactory$Builder_1.with(DesirePathsDataHolder.DESIRE_PATH_PROPERTY);
    }

}
