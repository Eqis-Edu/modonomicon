// SPDX-FileCopyrightText: 2023 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.modonomicon.client.gui.book;

import com.klikli_dev.modonomicon.book.BookCategory;
import com.klikli_dev.modonomicon.book.entries.BookEntry;
import com.klikli_dev.modonomicon.bookstate.BookUnlockStateManager;
import com.klikli_dev.modonomicon.bookstate.visual.CategoryVisualState;
import com.klikli_dev.modonomicon.client.gui.book.entry.EntryDisplayState;
import net.minecraft.client.Minecraft;

/**
 * A screen that represents a book. It usually manages other screens for categories and entries.
 */
public interface BookCategoryScreen {

    void onDisplay();

    void onClose();

    void loadState(CategoryVisualState state);

    void saveState(CategoryVisualState state);

    BookCategory getCategory();

    default EntryDisplayState getEntryDisplayState(BookEntry entry) {
        var player = Minecraft.getInstance().player;

        var isEntryUnlocked = BookUnlockStateManager.get().isUnlockedFor(player, entry);

        var anyParentsUnlocked = false;
        var allParentsUnlocked = true;
        for (var parent : entry.getParents()) {
            if (!BookUnlockStateManager.get().isUnlockedFor(player, parent.getEntry())) {
                allParentsUnlocked = false;
            } else {
                anyParentsUnlocked = true;
            }
        }

        if (entry.showWhenAnyParentUnlocked() && !anyParentsUnlocked)
            return EntryDisplayState.HIDDEN;

        if (!entry.showWhenAnyParentUnlocked() && !allParentsUnlocked)
            return EntryDisplayState.HIDDEN;

        if (!isEntryUnlocked)
            return entry.hideWhileLocked() ? EntryDisplayState.HIDDEN : EntryDisplayState.LOCKED;

        return EntryDisplayState.UNLOCKED;
    }
}
