/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.modonomicon.networking;

import com.klikli_dev.modonomicon.Modonomicon;
import com.klikli_dev.modonomicon.client.gui.BookGuiManager;
import com.klikli_dev.modonomicon.client.gui.book.BookAddress;
import com.klikli_dev.modonomicon.data.BookDataManager;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class OpenBookOnClientMessage implements Message {

    public static final Type<OpenBookOnClientMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Modonomicon.MOD_ID, "open_book_on_client"));

    public static final StreamCodec<RegistryFriendlyByteBuf, OpenBookOnClientMessage> STREAM_CODEC = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC,
            (m) -> m.bookId,
            OpenBookOnClientMessage::new
    );

    public ResourceLocation bookId;

    public OpenBookOnClientMessage(ResourceLocation bookId) {
        this.bookId = bookId;
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    @Override
    public void onClientReceived(Minecraft minecraft, Player player) {
        var book = BookDataManager.get().getBook(this.bookId);
        if (book != null) {
            BookGuiManager.get().openBook(BookAddress.defaultFor(book));
        }
    }
}
