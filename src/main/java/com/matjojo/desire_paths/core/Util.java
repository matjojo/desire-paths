package com.matjojo.desire_paths.core;

import com.matjojo.desire_paths.init.DesirePathInitializer;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

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
}
