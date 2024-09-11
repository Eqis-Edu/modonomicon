// SPDX-FileCopyrightText: 2024 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.modonomicon.datagen.book.demo;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.SingleBookSubProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookCategoryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.datagen.book.demo.formatting.AdvancedFormattingEntry;
import com.klikli_dev.modonomicon.datagen.book.demo.formatting.AlwaysLockedEntry;
import com.klikli_dev.modonomicon.datagen.book.demo.formatting.BasicFormattingEntry;
import com.klikli_dev.modonomicon.datagen.book.demo.formatting.LinkFormattingEntry;
import net.minecraft.world.item.Items;

public class FormattingCategory extends CategoryProvider {
    public static final String ID = "formatting";

    public FormattingCategory(SingleBookSubProvider parent) {
        super(parent);
    }

    @Override
    protected String[] generateEntryMap() {
        return new String[]{
                "_____________________",
                "__b___a______________",
                "__________l_____x____",
                "_____________________",
                "_____________________"
        };
    }

    @Override
    protected void generateEntries() {
        var basicFormattingEntry = this.add(new BasicFormattingEntry(this).generate('b'));

        var advancedFormattingEntry = this.add(new AdvancedFormattingEntry(this).generate('a'))
                .withParent(this.parent(basicFormattingEntry));

        var linkFormattingEntry = this.add(new LinkFormattingEntry(this).generate('l'))
                .withParent(advancedFormattingEntry);

        var alwaysLockedEntry = this.add(new AlwaysLockedEntry(this).generate('x'));
    }

    @Override
    protected BookCategoryModel additionalSetup(BookCategoryModel category) {
        //When first opening the category, open the basic formatting entry automatically.
        return category.withEntryToOpen(this.modLoc(ID, BasicFormattingEntry.ID), true);
    }

    @Override
    protected String categoryName() {
        return "Formatting Category";
    }

    @Override
    protected BookIconModel categoryIcon() {
        return BookIconModel.create(Items.BOOK);
    }

    @Override
    public String categoryId() {
        return ID;
    }
}
