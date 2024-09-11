/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.modonomicon.data;

import com.google.gson.JsonObject;
import com.klikli_dev.modonomicon.book.conditions.BookCondition;
import com.klikli_dev.modonomicon.book.page.BookPage;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;

public interface BookConditionJsonLoader<T extends BookCondition> extends JsonLoader<T> {
    T fromJson(ResourceLocation conditionParentId, JsonObject json, HolderLookup.Provider provider);

    /**
     * Deserializes the model from json.
     * @deprecated use {@link #fromJson(ResourceLocation, JsonObject, HolderLookup.Provider)} instead.
     */
    @Override
    @Deprecated(forRemoval = true, since="1.21.1-1.105.0")
    default T fromJson(JsonObject json, HolderLookup.Provider provider){
        //TODO(BookPageLoading): replace jsonloader with conditionpageloader entirely when removing this
        throw new UnsupportedOperationException("BookPageJsonLoaders must implement fromJson(ResourceLocation, JsonObject, HolderLookup.Provider)");
    }
}
