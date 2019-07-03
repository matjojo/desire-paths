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

@Mixin(net.minecraft.item.HoeItem.class)
public abstract class DesirePathHoeItemMixin {
    @Shadow @Final protected static Map<Block, BlockState> TILLED_BLOCKS;

    @Inject(at = @At(value = "RETURN"), method = "<init>(Lnet/minecraft/item/ToolMaterial;FLnet/minecraft/item/Item$Settings;)V")
    private void DesirePathHoeItemOnConstructorMixin(ToolMaterial toolMaterial_1, float float_1, Item.Settings item$Settings_1, CallbackInfo ci) {
        for (Block toChange : DesirePathBlocks.getBlocks()) {
            // we get this via the instance due to access issues that the mixin would solve, but that the IDE does not understand
            //noinspection AccessStaticViaInstance
            this.TILLED_BLOCKS.put(toChange, Blocks.FARMLAND.getDefaultState());
        }
    }
}
