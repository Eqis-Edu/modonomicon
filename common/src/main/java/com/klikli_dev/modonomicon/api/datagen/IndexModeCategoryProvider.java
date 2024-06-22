/*
 * SPDX-FileCopyrightText: 2023 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.modonomicon.api.datagen;

import com.klikli_dev.modonomicon.api.datagen.book.BookCategoryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryParentModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.book.BookDisplayMode;
import net.minecraft.util.StringUtil;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A category provider with reasonable defaults for index mode categories, includes setting the mode in #additionalSetup.
 */
public abstract class IndexModeCategoryProvider extends CategoryProvider {

    public IndexModeCategoryProvider(ModonomiconProviderBase parent) {
        super(parent);
    }

    @Override
    protected String[] generateEntryMap() {
        //Index mode categories don't need an entry map.
        //However, make sure not to query it either, otherwise you will get an unhappy surprise!
        return new String[0];
    }

    @Override
    protected BookCategoryModel additionalSetup(BookCategoryModel category) {
        //This makes this one category display in index mode.
        //If you want the whole book in index mode, do the same thing in the (single)book provider
        return category.withDisplayMode(BookDisplayMode.INDEX);
    }
}