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

import com.matjojo.desire_paths.data.DesirePathsDataHolder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.stat.Stat;
import net.minecraft.stat.Stats;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.stat.StatHandler.class)
public abstract class OnIncreaseStatMixin {

    @Inject(method = "increaseStat(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/stat/Stat;I)V", at = @At("HEAD"))
    private void DesirePathOnIncreaseStatMixin(PlayerEntity player, Stat stat, int amount, CallbackInfo callbackInfo) {
        if (stat == Stats.CUSTOM.getOrCreateStat(Stats.WALK_ONE_CM) ||
                stat == Stats.CUSTOM.getOrCreateStat(Stats.SPRINT_ONE_CM) &&
                        amount > DesirePathsDataHolder.speedThreshold) {
            // There has been trampling
            DesirePathsDataHolder.triggerTrample(player);
        }
    }
}

