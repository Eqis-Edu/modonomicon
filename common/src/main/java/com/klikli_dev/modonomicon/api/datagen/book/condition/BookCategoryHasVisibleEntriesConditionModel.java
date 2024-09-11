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
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class BookCategoryHasVisibleEntriesConditionModel extends BookConditionModel<BookCategoryHasVisibleEntriesConditionModel> {
    private ResourceLocation categoryId;

    protected BookCategoryHasVisibleEntriesConditionModel() {
        super(Condition.CATEGORY_HAS_VISIBLE_ENTRIES);
    }

    public static BookCategoryHasVisibleEntriesConditionModel create() {
        return new BookCategoryHasVisibleEntriesConditionModel();
    }


    @Override
    public JsonObject toJson(ResourceLocation conditionParentId, HolderLookup.Provider provider) {
        var json = super.toJson(conditionParentId, provider);

        if (this.categoryId.getNamespace().equals(conditionParentId.getNamespace()))
            json.addProperty("category_id", this.categoryId.getPath());
        else
            json.addProperty("category_id", this.categoryId.toString());

        return json;
    }

    public ResourceLocation getCategoryId() {
        return this.categoryId;
    }


    public BookCategoryHasVisibleEntriesConditionModel withCategory(ResourceLocation entryId) {
        this.categoryId = entryId;
        return this;
    }


    public BookCategoryHasVisibleEntriesConditionModel withCategory(String entryId) {
        this.categoryId = ResourceLocation.parse(entryId);
        return this;
    }

    @Override
    public BookCategoryHasVisibleEntriesConditionModel withTooltip(Component tooltip) {
        this.tooltip = tooltip;
        return this;
    }

    /**
     * Will overwrite withTooltip
     */
    @Override
    public BookCategoryHasVisibleEntriesConditionModel withTooltipString(String tooltipString) {
        this.tooltipString = tooltipString;
        return this;
    }
}
