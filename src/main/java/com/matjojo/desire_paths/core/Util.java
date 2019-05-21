package com.matjojo.desire_paths.core;

import com.matjojo.desire_paths.init.DesirePathInitializer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ViewableWorld;

public class Util {

    public static ItemStack getItemStackForTrampleableBlock(Block block) {
        ItemStack returnable = new ItemStack(Items.DIRT);

        if (block.equals(DesirePathInitializer.GRASS_DIRT_INTER)) {
            returnable = new ItemStack(Items.GRASS_BLOCK);
        } else if (block.equals(DesirePathInitializer.DIRT_COARSE_INTER)) {
            returnable = new ItemStack(Items.DIRT);
        } else if (block.equals(DesirePathInitializer.MYCELIUM_DIRT_INTER)) {
            returnable = new ItemStack(Items.MYCELIUM);
        } else if (block.equals(DesirePathInitializer.PODZOL_DIRT_INTER)) {
            returnable = new ItemStack(Items.PODZOL);
        }
        return returnable;
    }

    public static String getBlockNameStringForTrampleableBlock(Block block) {
        String blockName = "Trampleable";
        if (block.equals(DesirePathInitializer.GRASS_DIRT_INTER)) {
            blockName = "Grass";
        } else if (block.equals(DesirePathInitializer.DIRT_COARSE_INTER)) {
            blockName = "Dirt";
        } else if (block.equals(DesirePathInitializer.PODZOL_DIRT_INTER)) {
            blockName = "Podzol";
        } else if (block.equals(DesirePathInitializer.MYCELIUM_DIRT_INTER)) {
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


}
