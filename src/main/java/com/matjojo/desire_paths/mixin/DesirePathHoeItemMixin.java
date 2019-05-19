package com.matjojo.desire_paths.mixin;

import com.matjojo.desire_paths.data.DesirePathsDataHolder;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(net.minecraft.item.HoeItem.class)
public class DesirePathHoeItemMixin {

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
    @Inject(at = @At(value = "RETURN", ordinal = 1), method = "useOnBlock(Lnet/minecraft/item/ItemUsageContext;)Lnet/minecraft/util/ActionResult;",cancellable = true)
    private void DesirePathHoeItemHoeTrampledBlocksMixin(ItemUsageContext usageContext, CallbackInfoReturnable<ActionResult> cir) {
        // We get here from the pass return, so we need to check for the validity again.
        World world = usageContext.getWorld();
        BlockPos blockPosOfToHoeBlock = usageContext.getBlockPos();

        if (!(usageContext.getFacing() != Direction.DOWN && world.getBlockState(blockPosOfToHoeBlock.up()).isAir())) {
            return; // if the block is not valid to change we just let it return ActionResult.PASS
        }

        if (! world.getBlockState(blockPosOfToHoeBlock).getProperties().contains(DesirePathsDataHolder.DESIRE_PATH_PROPERTY)) {
            return; // if this is not one of our blocks we just let it return ActionResult.PASS
        }

        BlockState farmLand = Blocks.FARMLAND.getDefaultState();
        PlayerEntity player = usageContext.getPlayer();
        world.playSound(player, blockPosOfToHoeBlock, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
        if (!world.isClient) {
            world.setBlockState(blockPosOfToHoeBlock, farmLand, 11);
            if (player != null) {
                usageContext.getItemStack().applyDamage(1,
                        player,
                        (playerEntity) -> playerEntity.sendToolBreakStatus(usageContext.getHand()));
            }
        } // for the return value it does not matter if we are on client or server
        cir.setReturnValue(ActionResult.SUCCESS);
    }
}
