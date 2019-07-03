package com.matjojo.desire_paths.core;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class BlockIntoFarmLandOrPath {

    public static boolean dontAllowItemUsageContextForShovelOrHoe(ItemUsageContext usageContext) {

        World world = usageContext.getWorld();
        BlockPos blockPosOfToHoeBlock = usageContext.getBlockPos();
        // do not allow if
        return usageContext.getPlayerFacing() == Direction.DOWN ||                // we are at the bottom of the block
                !world.getBlockState(blockPosOfToHoeBlock.up()).isAir() ||  // the block above is not air
                !world.getBlockState(blockPosOfToHoeBlock).getProperties().contains(Util.DESIRE_PATH_PROPERTY);
    }                                                                       // or the block is not one of ours


    public static void doWorldInteractionForBlockChange(ItemUsageContext usageContext, SoundEvent soundEvent, BlockState intendedNewBlock) {
        World world = usageContext.getWorld();
        BlockPos blockPosOfToHoeBlock = usageContext.getBlockPos();
        PlayerEntity player = usageContext.getPlayer();
        world.playSound(player, blockPosOfToHoeBlock, soundEvent, SoundCategory.BLOCKS, 1.0F, 1.0F);
        if (!world.isClient) {
            world.setBlockState(blockPosOfToHoeBlock, intendedNewBlock, 11);
            if (player != null) {
                usageContext.getStack().damage(1,
                        player,
                        (playerEntity) -> playerEntity.sendToolBreakStatus(usageContext.getHand()));
            }
        } // for the return value it does not matter if we are on client or server
    }


}
