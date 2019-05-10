//Copyright (C) 2019  Matjojo
//
//        This program is free software: you can redistribute it and/or modify
//        it under the terms of the GNU General Public License as published by
//        the Free Software Foundation, either version 3 of the License, or
//        (at your option) any later version.
//
//        This program is distributed in the hope that it will be useful,
//        but WITHOUT ANY WARRANTY; without even the implied warranty of
//        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//        GNU General Public License for more details.
//
//        You should have received a copy of the GNU General Public License
//        along with this program.  If not, see <https://www.gnu.org/licenses/>.

package com.matjojo.desire_paths.init;

import com.matjojo.desire_paths.data.Blocks.Trampleable;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class DesirePathInitializer implements ModInitializer {

    public static final Block DIRT_COARSE_INTER = new Trampleable(FabricBlockSettings.copy(Blocks.DIRT).ticksRandomly().build());
    public static final Block GRASS_DIRT_INTER = new Trampleable(FabricBlockSettings.copy(Blocks.DIRT).ticksRandomly().build());
    public static final Block PODZOL_DIRT_INTER = new Trampleable(FabricBlockSettings.copy(Blocks.DIRT).ticksRandomly().build());
    public static final Block MYCELIUM_DIRT_INTER = new Trampleable(FabricBlockSettings.copy(Blocks.DIRT).ticksRandomly().build());

    @Override
    public void onInitialize()
    {
        Registry.register(Registry.BLOCK, new Identifier("desire-paths", "dirt_coarse_inter"), DIRT_COARSE_INTER);
        Registry.register(Registry.BLOCK, new Identifier("desire-paths", "grass_dirt_inter"), GRASS_DIRT_INTER);
        Registry.register(Registry.BLOCK, new Identifier("desire-paths", "podzol_dirt_inter"), PODZOL_DIRT_INTER);
        Registry.register(Registry.BLOCK, new Identifier("desire-paths", "mycelium_dirt_inter"), MYCELIUM_DIRT_INTER);
    }
}
