package com.matjojo.desire_paths.mixin;

import com.matjojo.desire_paths.data.Blocks.DesirePathBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(net.minecraft.entity.ai.goal.EatGrassGoal.class)
public abstract class DesirePathEatGrassGoalMixin {

    @Shadow
    @Final
    private World world;

    @Shadow
    @Final
    private MobEntity mob;

    @Shadow
    private int timer;

    /**
     * The current (Mappings 10, version 1.14.1) relevant bytecode: <code>
     * 67: getfield      #29                 // Field world:Lnet/minecraft/world/World;
     * 70: aload_1
     * 71: invokevirtual #90                 // Method net/minecraft/util/math/BlockPos.down:()Lnet/minecraft/util/math/BlockPos;
     * 74: invokevirtual #80                 // Method net/minecraft/world/World.getBlockState:(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;
     * 77: invokevirtual #96                 // Method net/minecraft/block/BlockState.getBlock:()Lnet/minecraft/block/Block;
     * 80: getstatic     #102                // Field net/minecraft/block/Blocks.GRASS_BLOCK:Lnet/minecraft/block/Block;
     * 83: if_acmpne     88
     * 86: iconst_1
     * 87: ireturn				           // return opcode 2
     * 88: iconst_0
     * 89: ireturn				           // return opcode 3
     * </code>
     * As you can see we need to inject into return opcde 3 to inject at the false return we want to edit
     *
     * @reason To make sure Trampled blocks are also eatable
     * @author Matjojo
     */
    @Inject(at = @At(value = "RETURN", ordinal = 3), method = "canStart()Z", cancellable = true)
    private void DesirePathOnCanStartMixin(CallbackInfoReturnable<Boolean> cir) {
        // Since we mixin at the false return we know that this is not the grass option
        cir.setReturnValue(this.world.getBlockState(new BlockPos(this.mob).down()).getBlock() == DesirePathBlocks.GRASS_DIRT_INTER);
    }


    /**
     * @reason To make sure Trampled blocks are also eatable
     * @author Matjojo
     */
    @Inject(at = @At(value = "TAIL"), method = "tick()V")
    private void DesirePathOnTickMixin(CallbackInfo ci) {

        if (this.timer == 4) {
            BlockPos blockPosIsBeingEaten = new BlockPos(this.mob).down();
            if (this.world.getBlockState(blockPosIsBeingEaten).getBlock() == DesirePathBlocks.GRASS_DIRT_INTER) {
                if (this.world.getGameRules().getBoolean("mobGriefing")) {
                    // we leave the blockID being given as Grass block, since we are basically a grass block anyways,
                    // and if any other mod changes the grass blocks sounds and such those will now automatically
                    // also be used for our breaking event
                    this.world.playLevelEvent(2001, blockPosIsBeingEaten, Block.getRawIdFromState(Blocks.GRASS_BLOCK.getDefaultState()));
                    this.world.setBlockState(blockPosIsBeingEaten, Blocks.DIRT.getDefaultState(), 2);
                }
                this.mob.onEatingGrass();
            }
        }

    }


}
