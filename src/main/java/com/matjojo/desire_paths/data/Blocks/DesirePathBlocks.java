package com.matjojo.desire_paths.data.Blocks;

import com.matjojo.desire_paths.core.Util;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;

public class DesirePathBlocks {

    public static final Block DIRT_COARSE_INTER = new Trampleable(FabricBlockSettings.copy(Blocks.DIRT).ticksRandomly().dropsLike(Blocks.DIRT).build());
    public static final Block GRASS_DIRT_INTER = new Trampleable(FabricBlockSettings.copy(Blocks.DIRT).ticksRandomly().dropsLike(Blocks.GRASS_BLOCK).build());
    public static final Block PODZOL_DIRT_INTER = new Trampleable(FabricBlockSettings.copy(Blocks.DIRT).ticksRandomly().dropsLike(Blocks.PODZOL).build());
    public static final Block MYCELIUM_DIRT_INTER = new Trampleable(FabricBlockSettings.copy(Blocks.DIRT).ticksRandomly().dropsLike(Blocks.MYCELIUM).build());


    public static void registerBlocks() {
        Registry.register(Registry.BLOCK, Util.getIdentifier("dirt_coarse_inter"), DIRT_COARSE_INTER);
        Registry.register(Registry.BLOCK, Util.getIdentifier("grass_dirt_inter"), GRASS_DIRT_INTER);
        Registry.register(Registry.BLOCK, Util.getIdentifier("podzol_dirt_inter"), PODZOL_DIRT_INTER);
        Registry.register(Registry.BLOCK, Util.getIdentifier("mycelium_dirt_inter"), MYCELIUM_DIRT_INTER);
    }

}
