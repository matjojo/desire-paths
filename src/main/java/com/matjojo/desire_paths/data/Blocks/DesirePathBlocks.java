package com.matjojo.desire_paths.data.Blocks;

import com.google.common.collect.Sets;
import com.matjojo.desire_paths.core.Util;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;

import java.util.Set;

public class DesirePathBlocks {

    public static final Block DIRT_COARSE_INTER = new Trampleable(FabricBlockSettings.copy(Blocks.DIRT).ticksRandomly().dropsLike(Blocks.DIRT).build(), DesirePathBlock.DIRT_COARSE_INTER);
    public static final Block GRASS_DIRT_INTER = new Trampleable(FabricBlockSettings.copy(Blocks.GRASS_BLOCK).ticksRandomly().dropsLike(Blocks.GRASS_BLOCK).build(), DesirePathBlock.GRASS_DIRT_INTER);
    public static final Block PODZOL_DIRT_INTER = new Trampleable(FabricBlockSettings.copy(Blocks.PODZOL).ticksRandomly().dropsLike(Blocks.PODZOL).build(), DesirePathBlock.PODZOL_DIRT_INTER);
    public static final Block MYCELIUM_DIRT_INTER = new Trampleable(FabricBlockSettings.copy(Blocks.MYCELIUM).ticksRandomly().dropsLike(Blocks.MYCELIUM).build(), DesirePathBlock.MYCELIUM_DIRT_INTER);

    private static final Set<Block> BLOCK_LIST = Sets.newHashSet(DIRT_COARSE_INTER, GRASS_DIRT_INTER, PODZOL_DIRT_INTER, MYCELIUM_DIRT_INTER);

    public static void registerBlocks() {
        Registry.register(Registry.BLOCK, Util.getIdentifier("dirt_coarse_inter"), DIRT_COARSE_INTER);
        Registry.register(Registry.BLOCK, Util.getIdentifier("grass_dirt_inter"), GRASS_DIRT_INTER);
        Registry.register(Registry.BLOCK, Util.getIdentifier("podzol_dirt_inter"), PODZOL_DIRT_INTER);
        Registry.register(Registry.BLOCK, Util.getIdentifier("mycelium_dirt_inter"), MYCELIUM_DIRT_INTER);

    }

    public static Set<Block> getBlocks() {
        return BLOCK_LIST;
    }

}
