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
//

package com.matjojo.desire_paths.init;

import com.matjojo.desire_paths.data.Blocks.DesirePathBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.client.render.RenderLayer;

public class DesirePathClientInitializer implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ColorProviderRegistry.BLOCK.register((block, pos, world, layer) -> {
            BlockColorProvider provider = ColorProviderRegistry.BLOCK.get(Blocks.GRASS);
            return provider == null ? -1 : provider.getColor(block, pos, world, layer);
        }, DesirePathBlocks.GRASS_DIRT_INTER);

		for (Block block : DesirePathBlocks.getBlocks()) {
		    BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutoutMipped());
		}
    }
}