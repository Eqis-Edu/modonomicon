/*
 *
 *  * SPDX-FileCopyrightText: 2022 klikli-dev
 *  *
 *  * SPDX-License-Identifier: MIT
 *
 */

package com.klikli_dev.modonomicon.api.datagen.book.condition;

import com.google.gson.JsonObject;
import com.klikli_dev.modonomicon.api.ModonomiconConstants.Data.Condition;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;

public class BookEntryReadConditionModel extends BookConditionModel<BookEntryReadConditionModel> {
    protected ResourceLocation entryId;

    protected BookEntryReadConditionModel() {
        super(Condition.ENTRY_READ);
    }

    public static BookEntryReadConditionModel create() {
        return new BookEntryReadConditionModel();
    }

    @Override
    public JsonObject toJson(ResourceLocation conditionParentId, HolderLookup.Provider provider) {
        var json = super.toJson(conditionParentId, provider);

        if (this.entryId.getNamespace().equals(conditionParentId.getNamespace()))
            json.addProperty("entry_id", this.entryId.getPath());
        else
            json.addProperty("entry_id", this.entryId.toString());

        return json;
    }

    public ResourceLocation getEntryId() {
        return this.entryId;
    }

    public BookEntryReadConditionModel withEntry(ResourceLocation entryId) {
        this.entryId = entryId;
        return this;
    }

    public BookEntryReadConditionModel withEntry(String entryId) {
        this.entryId = ResourceLocation.parse(entryId);
        return this;
    }
}
