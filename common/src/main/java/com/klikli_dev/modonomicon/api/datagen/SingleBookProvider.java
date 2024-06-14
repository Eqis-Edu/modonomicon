/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */


package com.klikli_dev.modonomicon.api.datagen;

import com.klikli_dev.modonomicon.api.datagen.book.BookCategoryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookModel;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.util.StringUtil;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * An opinionated book provider with helper methods to generate a single book more easily.
 */
public abstract class SingleBookProvider extends BookProvider {
    protected BookModel book;
    protected String bookId;
    protected int currentSortIndex;

    /**
     * @param defaultLang The LanguageProvider to fill with this book provider. IMPORTANT: the Language Provider needs to be added to the DataGenerator AFTER the BookProvider.
     */
    public SingleBookProvider(String bookId, PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries, String modId, ModonomiconLanguageProvider defaultLang, ModonomiconLanguageProvider... translations) {
        this(bookId, packOutput, registries, modId, defaultLang, makeLangMap(defaultLang, translations));
    }

    /**
     * @param defaultLang The LanguageProvider to fill with this book provider. IMPORTANT: the Language Provider needs to be added to the DataGenerator AFTER the BookProvider.
     */
    public SingleBookProvider(String bookId, PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries, String modId, ModonomiconLanguageProvider defaultLang, Map<String, ModonomiconLanguageProvider> translations) {
        super(packOutput, registries, modId, defaultLang, translations);
        this.book = null;

        this.bookId = bookId;
        this.currentSortIndex = 0;
    }

    public String bookId() {
        return this.bookId;
    }

    @Override
    public String getName() {
        return "Book: " + this.modId() + ":" + this.bookId();
    }

    protected BookCategoryModel add(BookCategoryModel category) {
        if (category.getSortNumber() == -1) {
            category.withSortNumber(this.currentSortIndex++);
        }
        this.book.withCategory(category);
        return category;
    }

    /**
     * Only override if you know what you are doing.
     * Generally you should not.
     */
    protected void generate() {
        this.context().book(this.bookId());

        var book = BookModel.create(this.modLoc(this.bookId), this.context().bookName());

        this.add(this.context().bookName(), this.bookName());
        this.add(this.context().bookTooltip(), this.bookTooltip());
        var bookDescription = this.bookDescription();
        if (!StringUtil.isNullOrEmpty(bookDescription)) {
            this.add(this.context().bookDescription(), bookDescription);
            book.withDescription(this.context().bookDescription());
        }

        this.book = this.additionalSetup(book);

        this.generateCategories();

        this.add(book);
    }

    /**
     * Implement this and in it generate and .add() your categories.
     * Context already is set to this book.
     */
    protected abstract void generateCategories();

    /**
     * Implement this and modify the book as needed for additional config.
     * Categories should not be added here, instead call .add() in generateCategories().
     * Context already is set to this book.
     */
    protected BookModel additionalSetup(BookModel book) {
        return book;
    }

    /**
     * Implement this and return the book name in the main language.
     */
    protected abstract String bookName();

    /**
     * Implement this and return the book tooltip in the main language.
     */
    protected abstract String bookTooltip();

    /**
     * Implement this and return the book description in the main language.
     */
    protected String bookDescription() {
        return "";
    }
}
