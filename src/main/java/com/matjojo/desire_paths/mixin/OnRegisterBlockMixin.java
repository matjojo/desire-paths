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
package com.matjojo.desire_paths.mixin;

import com.matjojo.desire_paths.data.Blocks.GenericTrampleable;
import com.matjojo.desire_paths.data.Blocks.SnowyTrampleable;
import com.matjojo.desire_paths.data.DesirePathsDataHolder;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(net.minecraft.block.Blocks.class)
public abstract class OnRegisterBlockMixin {

    @Inject(at = @At("HEAD"), method = "register(Ljava/lang/String;Lnet/minecraft/block/Block;)Lnet/minecraft/block/Block;", cancellable = true)
    private static void DesirePathOnRegisterBlock(String blockName, Block block, CallbackInfoReturnable cir) {
        if (DesirePathsDataHolder.shouldInjectGenericTrampleableBlock(blockName)) {
            // these are the ones we want to make from our class
            cir.setReturnValue(Registry.register(Registry.BLOCK,
                    blockName,
                    new GenericTrampleable(
                            FabricBlockSettings.of(
                                    Material.EARTH,
                                    MaterialColor.DIRT)
                                    .strength(0.5F, 0.5F)
                                    .sounds(BlockSoundGroup.GRAVEL)
                                    .build())));
        } else if (DesirePathsDataHolder.shouldInjectSnowyTrampleableBlock(blockName)) {
            cir.setReturnValue(Registry.register(Registry.BLOCK,
                    blockName,
                    new SnowyTrampleable(
                            FabricBlockSettings.of(
                                    Material.EARTH,
                                    MaterialColor.SPRUCE)
                                    .strength(0.5F, 0.5F)
                                    .sounds(BlockSoundGroup.GRAVEL)
                                    .build())));
        }
    }
}
