/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.modonomicon.api.datagen.book;

import com.google.gson.JsonObject;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;

public class BookEntryParentModel {
    protected ResourceLocation entryId;
    protected boolean drawArrow = true;
    protected boolean lineEnabled = true;
    protected boolean lineReversed = false;

    protected BookEntryParentModel(ResourceLocation entryId) {
        this.entryId = entryId;
    }

    public static BookEntryParentModel create(ResourceLocation entryId) {
        return new BookEntryParentModel(entryId);
    }

    /**
     * @param ownerEntryId the entry id of the entry that contains this parent information. This is the CHILD not the parent.
     * @param provider a registry / holder lookup provider.
     */
    public JsonObject toJson(ResourceLocation ownerEntryId, HolderLookup.Provider provider) {
        JsonObject json = new JsonObject();

        //if we are in the same namespace, which we basically always should be, omit namespace
        if (this.entryId.getNamespace().equals(ownerEntryId.getNamespace()))
            json.addProperty("entry", this.entryId.getPath());
        else
            json.addProperty("entry", this.entryId.toString());

        json.addProperty("draw_arrow", this.drawArrow);
        json.addProperty("line_enabled", this.lineEnabled);
        json.addProperty("line_reversed", this.lineReversed);
        return json;
    }

    public ResourceLocation getEntryId() {
        return this.entryId;
    }

    public boolean isDrawArrow() {
        return this.drawArrow;
    }

    public boolean isLineEnabled() {
        return this.lineEnabled;
    }

    public boolean isLineReversed() {
        return this.lineReversed;
    }

    public BookEntryParentModel withEntryId(ResourceLocation entryId) {
        this.entryId = entryId;
        return this;
    }

    public BookEntryParentModel withDrawArrow(boolean drawArrow) {
        this.drawArrow = drawArrow;
        return this;
    }

    public BookEntryParentModel withLineEnabled(boolean lineEnabled) {
        this.lineEnabled = lineEnabled;
        return this;
    }

    public BookEntryParentModel withLineReversed(boolean lineReversed) {
        this.lineReversed = lineReversed;
        return this;
    }
}
