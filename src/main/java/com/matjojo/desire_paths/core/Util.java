package com.matjojo.desire_paths.core;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.FluidState;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.state.property.IntegerProperty;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ViewableWorld;

public class Util {
    public static final IntegerProperty DESIRE_PATH_PROPERTY;

    public static final TranslatableComponent DESIRE_PATHS_MOD_NAME;

    static {
        DESIRE_PATH_PROPERTY = IntegerProperty.create("desiretramples", 0, TrampleUtil.MAX_TRAMPLE);

        DESIRE_PATHS_MOD_NAME = new TranslatableComponent("desirepaths.hwylamodname");
    }

    public static boolean blockIsNextToValidWater(ViewableWorld world, BlockPos position) {
        for (Direction offset : Direction.Type.HORIZONTAL) {
            BlockState blockNextTo = world.getBlockState(position.offset(offset));
            FluidState fluidNextTo = world.getFluidState(position.offset(offset));
            if (fluidNextTo.matches(FluidTags.WATER) || blockNextTo.getBlock() == Blocks.FROSTED_ICE) {
                return true;
            }
        }
        return false;
    }

    public static Identifier getIdentifier(String whatFor) {
        return new Identifier("desire-paths", whatFor);
    }

}
