// SPDX-FileCopyrightText: 2023 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.modonomicon.client.gui.book;

import com.klikli_dev.modonomicon.book.Book;
import net.minecraft.network.chat.Component;

import java.util.List;

public interface BookScreenWithButtons {
    Book getBook();

    void setTooltip(List<Component> tooltip);

    default void setTooltip(Component... strings) {
        this.setTooltip(List.of(strings));
    }
}
