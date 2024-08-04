// SPDX-FileCopyrightText: 2024 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.modonomicon.api.events;

import com.klikli_dev.modonomicon.client.gui.book.entry.EntryDisplayState;
import net.minecraft.resources.ResourceLocation;

/**
 * An event that is fired on the client-side when an entry is clicked in the book.
 * If the event is cancelled by a listener, the entry will not be displayed.
 */
public class EntryClickedEvent extends ModonomiconEvent {
    protected ResourceLocation bookId;
    protected ResourceLocation entryId;

    protected double mouseX;
    protected double mouseY;
    protected int button;

    protected EntryDisplayState displayState;

    public EntryClickedEvent(ResourceLocation bookId, ResourceLocation entryId, double mouseX, double mouseY, int button, EntryDisplayState displayState) {
        super(true);

        this.bookId = bookId;
        this.entryId = entryId;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.button = button;
        this.displayState = displayState;
    }

    public ResourceLocation getBookId() {
        return this.bookId;
    }

    public ResourceLocation getEntryId() {
        return this.entryId;
    }

    /**
     * For categories in Index mode this is the X coordinate of the button that was clicked, instead of the mouse cursor that clicked it.
     */
    public double getMouseX() {
        return this.mouseX;
    }

    /**
     * For categories in Index mode this is the Y coordinate of the button that was clicked, instead of the mouse cursor that clicked it.
     */
    public double getMouseY() {
        return this.mouseY;
    }

    /**
     * For categories in Index mode this is always GLFW_MOUSE_BUTTON_1 (= 0 = left mouse button).
     */
    public int getButton() {
        return this.button;
    }

    public EntryDisplayState getDisplayState() {
        return this.displayState;
    }
}
