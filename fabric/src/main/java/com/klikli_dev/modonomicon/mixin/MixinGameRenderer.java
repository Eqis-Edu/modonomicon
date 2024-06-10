/*
 * SPDX-FileCopyrightText: 2023 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.modonomicon.mixin;

import com.klikli_dev.modonomicon.client.ClientTicks;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class MixinGameRenderer {

    @Inject(at = @At("HEAD"), method = "render(Lnet/minecraft/client/DeltaTracker;Z)V")
    public void renderHead(DeltaTracker deltaTracker, boolean bl, CallbackInfo info) {
        ClientTicks.renderTickStart(deltaTracker.getGameTimeDeltaPartialTick(bl));
    }

    @Inject(at = @At("RETURN"), method = "render(Lnet/minecraft/client/DeltaTracker;Z)V")
    public void renderReturn(DeltaTracker deltaTracker, boolean bl, CallbackInfo info) {
        ClientTicks.renderTickEnd();
    }
}
