/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.modonomicon.network;

import com.klikli_dev.modonomicon.networking.Message;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.network.CustomPayloadEvent;

public class MessageHandler {

    public static <T extends Message> void handle(T message, CustomPayloadEvent.Context ctx) {
        if (ctx.isServerSide()) {
            ctx.enqueueWork(() -> {
                handleServer(message, ctx);
            });
        } else {
            ctx.enqueueWork(() -> {
                //separate class to avoid loading client code on server.
                //Using OnlyIn on a method in this class would work too, but is discouraged
                ClientMessageHandler.handleClient(message, ctx);
            });
        }
        ctx.setPacketHandled(true);
    }

    public static <T extends Message> void handleServer(T message, CustomPayloadEvent.Context ctx) {
        MinecraftServer server = ctx.getSender().level().getServer();
        message.onServerReceived(server, ctx.getSender());
    }
}
