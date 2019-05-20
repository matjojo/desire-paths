package com.matjojo.desire_paths.core;

import com.matjojo.desire_paths.init.DesirePathInitializer;
import net.minecraft.block.Block;

public class BlockToString {

    public static String getBlockNameFormatted(Block block) {
        String blockName = "Block";
        if (block.equals(DesirePathInitializer.GRASS_DIRT_INTER)) {
            blockName = "Grass";
        } else if (block.equals(DesirePathInitializer.DIRT_COARSE_INTER)) {
            blockName = "Dirt";
        } else if (block.equals(DesirePathInitializer.PODZOL_DIRT_INTER)) {
            blockName = "Podzol";
        } else if (block.equals(DesirePathInitializer.MYCELIUM_DIRT_INTER)) {
            blockName = "Mycelium";
        }
        return "Trampled " + blockName;
    }

}
