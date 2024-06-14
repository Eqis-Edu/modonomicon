// SPDX-FileCopyrightText: 2024 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.modonomicon.datagen.book.demo;

import com.klikli_dev.modonomicon.api.datagen.BookProvider;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookCategoryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.datagen.book.demo.formatting.AlwaysLockedEntry;
import com.klikli_dev.modonomicon.datagen.book.demo.formatting.BasicFormattingEntry;
import net.minecraft.world.item.Items;

public class ConditionalCategory extends CategoryProvider {
    public static final String ID = "conditional";

    public ConditionalCategory(BookProvider parent) {
        super(parent);
    }

    @Override
    protected String[] generateEntryMap() {
        return new String[]{
                "_____________________",
                "_____________________",
                "__________l__________",
                "_____________________",
                "_____________________"
        };
    }

    @Override
    protected void generateEntries() {
        var alwaysLockedEntry = this.add(new AlwaysLockedEntry(this).generate('l'));
    }

    @Override
    protected BookCategoryModel additionalSetup(BookCategoryModel category) {
        //When first opening the category, open the basic formatting entry automatically.
        return category.withEntryToOpen(this.modLoc(ID, BasicFormattingEntry.ID), true);
    }

    @Override
    protected String categoryName() {
        return "Conditional Category";
    }

    @Override
    protected BookIconModel categoryIcon() {
        return BookIconModel.create(Items.CHEST);
    }

    @Override
    protected String categoryId() {
        return ID;
    }
}
