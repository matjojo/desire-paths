package com.matjojo.desire_paths.mixin;


import com.matjojo.desire_paths.core.Util;
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

    /**
     * Relevant bytecode (Return opcode numbering added by me)
     *
     *      139: getstatic     #208                // Field net/minecraft/tag/FluidTags.WATER:Lnet/minecraft/tag/Tag;
     *      142: invokeinterface #214,  2          // InterfaceMethod net/minecraft/fluid/FluidState.matches:(Lnet/minecraft/tag/Tag;)Z
     *      147: ifne          161
     *      150: aload         8
     *      152: invokevirtual #102                // Method net/minecraft/block/BlockState.getBlock:()Lnet/minecraft/block/Block;
     *      155: getstatic     #217                // Field net/minecraft/block/Blocks.FROSTED_ICE:Lnet/minecraft/block/Block;
     *      158: if_acmpne     163
     *      161: iconst_1
     *      162: ireturn		// return 1
     *      163: goto          85
     *      166: iconst_0
     *      167: ireturn		// return 2
     *
     *      As you can see by injecting at return opcode 2 we ensure that we only inject in those moments that the block might actually be ours.
     *
     *
     * @author Matjojo
     *
     * @reason To make sure sugarcane can also be placed on other blocks
     */
    @Inject(at = @At(value = "RETURN", ordinal = 2), method = "canPlaceAt(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/ViewableWorld;Lnet/minecraft/util/math/BlockPos;)Z", cancellable = true)
    private void DesirePathOnSugarCaneCanPlantOnTopOfMixin(BlockState blockState_1, ViewableWorld world, BlockPos intendedPosition, CallbackInfoReturnable<Boolean> cir) {
        if (world.getBlockState(intendedPosition.down())
                .getBlock()
                .getDefaultState()
                .getProperties()
                .contains(DesirePathsDataHolder.DESIRE_PATH_PROPERTY) &&
                Util.blockIsNextToValidWater(world, intendedPosition.down()) // and after that if the block is next to water
        ) {
            cir.setReturnValue(true);
        }
    }
}
