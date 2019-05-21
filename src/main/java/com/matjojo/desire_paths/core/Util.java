package com.matjojo.desire_paths.core;

import com.matjojo.desire_paths.data.Blocks.DesirePathBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.property.IntegerProperty;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ViewableWorld;

public class Util {
    public static final IntegerProperty DESIRE_PATH_PROPERTY;

    static {
        DESIRE_PATH_PROPERTY = IntegerProperty.create("desiretramples", 0, TrampleUtil.MAX_TRAMPLE);
    }

    public static ItemStack getItemStackForTrampleableBlock(Block block) {
        ItemStack returnable = new ItemStack(Items.DIRT);

        if (block.equals(DesirePathBlocks.GRASS_DIRT_INTER)) {
            returnable = new ItemStack(Items.GRASS_BLOCK);
        } else if (block.equals(DesirePathBlocks.DIRT_COARSE_INTER)) {
            returnable = new ItemStack(Items.DIRT);
        } else if (block.equals(DesirePathBlocks.MYCELIUM_DIRT_INTER)) {
            returnable = new ItemStack(Items.MYCELIUM);
        } else if (block.equals(DesirePathBlocks.PODZOL_DIRT_INTER)) {
            returnable = new ItemStack(Items.PODZOL);
        }
        return returnable;
    }

    public static String getBlockNameStringForTrampleableBlock(Block block) {
        String blockName = "Trampleable";
        if (block.equals(DesirePathBlocks.GRASS_DIRT_INTER)) {
            blockName = "Grass";
        } else if (block.equals(DesirePathBlocks.DIRT_COARSE_INTER)) {
            blockName = "Dirt";
        } else if (block.equals(DesirePathBlocks.PODZOL_DIRT_INTER)) {
            blockName = "Podzol";
        } else if (block.equals(DesirePathBlocks.MYCELIUM_DIRT_INTER)) {
            blockName = "Mycelium";
        }
        return blockName;
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
