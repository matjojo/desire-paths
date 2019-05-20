package com.matjojo.desire_paths.mixin;

import com.matjojo.desire_paths.core.BlockIntoFarmLandOrPath;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(net.minecraft.item.HoeItem.class)
public abstract class DesirePathHoeItemMixin {

    /**
     * Relevant byteCode:
     *
     *      111: invokedynamic #142,  0            // InvokeDynamic #0:accept:(Lnet/minecraft/item/ItemUsageContext;)Ljava/util/function/Consumer;
     *      116: invokevirtual #148                // Method net/minecraft/item/ItemStack.applyDamage:(ILnet/minecraft/entity/LivingEntity;Ljava/util/function/Consumer;)V
     *      119: getstatic     #154                // Field net/minecraft/util/ActionResult.SUCCESS:Lnet/minecraft/util/ActionResult;
     *      122: areturn				// Return 0
     *      123: getstatic     #157                // Field net/minecraft/util/ActionResult.PASS:Lnet/minecraft/util/ActionResult;
     *      126: areturn				// Return 1
     *
     * We want to capture the return ActionResult.PASS, if the hoe was used on our block we hoe it.
     *
     * @reason To Make sure the hoe can be used on trampled blocks
     * @author Matjojo
     */
    @SuppressWarnings("Duplicates")
    @Inject(at = @At(value = "RETURN", ordinal = 1), method = "useOnBlock(Lnet/minecraft/item/ItemUsageContext;)Lnet/minecraft/util/ActionResult;",cancellable = true)
    private void DesirePathHoeItemHoeTrampledBlocksMixin(ItemUsageContext usageContext, CallbackInfoReturnable<ActionResult> cir) {
        // We get here from the pass return, so we need to check for the validity again.
        if (BlockIntoFarmLandOrPath.dontAllowItemUsageContextForShovelOrHoe(usageContext)) {
            return;
        }
        // Now we can use a util function to call all the breaking and sound making events
        BlockIntoFarmLandOrPath.doWorldInteractionForBlockChange(usageContext, SoundEvents.ITEM_HOE_TILL, Blocks.FARMLAND.getDefaultState());
        cir.setReturnValue(ActionResult.SUCCESS);
    }
}
