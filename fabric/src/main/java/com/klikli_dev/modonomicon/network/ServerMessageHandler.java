/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.modonomicon.network;

import com.klikli_dev.modonomicon.networking.Message;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class ServerMessageHandler<T extends Message> implements ServerPlayNetworking.PlayPayloadHandler<T> {

    public ServerMessageHandler() {
    }

    @Override
    public void receive(T payload, ServerPlayNetworking.Context context) {
        context.player().getServer().execute(() -> {
            payload.onServerReceived(context.player().getServer(), context.player());
        });
    }
}
