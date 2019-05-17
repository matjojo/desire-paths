package com.matjojo.desire_paths.mixin;


import com.matjojo.desire_paths.data.DesirePathsDataHolder;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ViewableWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(net.minecraft.block.SugarCaneBlock.class)
public abstract class DesirePathsOnSugarCaneCanPlantOnTopOfMixin {

    @Inject(at = @At("RETURN"), method = "canPlaceAt(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/ViewableWorld;Lnet/minecraft/util/math/BlockPos;)Z", cancellable = true)
    private void DesirePathOnSugarCaneCanPlantOnTopOfMixin(BlockState blockState_1, ViewableWorld world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue()) { // if return is false, so the block might be ours
            System.out.println("Return was false, trying to edit now");
            if (world.getBlockState(pos.down())
                    .getBlock()
                    .getDefaultState()
                    .getProperties()
                    .contains(DesirePathsDataHolder.DESIRE_PATH_PROPERTY)) {
                // if the block is ours
                System.out.println("Setting the return to true  ");
                cir.setReturnValue(true);
            }
        } // else: Nothing, the return is already true
    }
}
