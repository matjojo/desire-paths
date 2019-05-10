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
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.entity.player.PlayerEntity.class)
public abstract class OnPlayerAddStatisticsMixin {


    @Inject(method = "method_7282(DDD)V", at = @At("RETURN"))
    private void onPlayerAddStatisticsMixin(double double_1, double double_2, double double_3, CallbackInfo ci) {
        //noinspection ConstantConditions
        Entity self = (Entity) (Object) this; // this is a bit of a hack but this is how it works with mixin.
        PlayerEntity player;
        try {
            //noinspection ConstantConditions
            player = (PlayerEntity) self;
        } catch (ClassCastException exception) {
            self.sendMessage(new TextComponent("An error occurred in the DesirePaths mod. Please report this to the mod creator."));
            self.sendMessage(new TextComponent("Please give this error log to the mod creator, it is also in the log."));
            self.sendMessage(new TextComponent(exception.getLocalizedMessage()));
            exception.printStackTrace();
            return;
        }

        if (!DesirePathsDataHolder.playerIsTrampling(
                player,
                Math.round(MathHelper.sqrt(double_1 * double_1 + double_2 * double_2 + double_3 * double_3) * 100.0F))
        ) {
            return;
        }

        DesirePathsDataHolder.triggerTrample(player);
    }
}