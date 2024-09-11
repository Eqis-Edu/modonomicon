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

public class BookEntryUnlockedConditionModel extends BookConditionModel<BookEntryUnlockedConditionModel> {
    protected ResourceLocation entryId;

    protected BookEntryUnlockedConditionModel() {
        super(Condition.ENTRY_UNLOCKED);
    }

    public static BookEntryUnlockedConditionModel create() {
        return new BookEntryUnlockedConditionModel();
    }

    public ResourceLocation getEntryId() {
        return this.entryId;
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

    public BookEntryUnlockedConditionModel withEntry(ResourceLocation entryId) {
        this.entryId = entryId;
        return this;
    }

    public BookEntryUnlockedConditionModel withEntry(String entryId) {
        this.entryId = ResourceLocation.parse(entryId);
        return this;
    }
}
