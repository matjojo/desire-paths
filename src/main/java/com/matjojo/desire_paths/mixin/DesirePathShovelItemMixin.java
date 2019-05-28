package com.matjojo.desire_paths.mixin;

import com.matjojo.desire_paths.data.Blocks.DesirePathBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.Set;

@Mixin(net.minecraft.item.ShovelItem.class)
public abstract class DesirePathShovelItemMixin {

    @Shadow @Final private static Set<Block> EFFECTIVE_BLOCKS;

    @Shadow @Final protected static Map<Block, BlockState> BLOCK_TRANSFORMATIONS_MAP;

    @Inject(at = @At(value = "RETURN"), method = "<init>(Lnet/minecraft/item/ToolMaterial;FFLnet/minecraft/item/Item$Settings;)V")
    private void DesirePathShovelItemOnConstructorMixin(ToolMaterial toolMaterial_1, float float_1, float float_2, Item.Settings item$Settings_1, CallbackInfo ci) {
        // no inspection since a private static member so unable to use the static access.
        //noinspection AccessStaticViaInstance
        this.EFFECTIVE_BLOCKS.addAll(DesirePathBlocks.getBlocks());

        for (Block turning : DesirePathBlocks.getBlocks()) {
            // no inspection since a private static member so unable to use the static access.
            //noinspection AccessStaticViaInstance
            this.BLOCK_TRANSFORMATIONS_MAP.put(turning, Blocks.GRASS_PATH.getDefaultState());
        }
    }
}
