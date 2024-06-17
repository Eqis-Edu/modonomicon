/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */


package com.klikli_dev.modonomicon.api.datagen;

import com.klikli_dev.modonomicon.api.datagen.book.BookCategoryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookModel;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;
import java.util.function.BiConsumer;

/**
 * A book sup provider that is oriented on the legacy book provider API for easier migration.
 * It still needs to be handed over to a book provider!
 */
public abstract class LegacyBookProvider extends ModonomiconProviderBase implements BookSubProvider {
    protected BookModel book;
    protected String bookId;
    protected int currentSortIndex;

    /**
     * Copy of the old constructor to keep compatibility, despite not needing all parameters.
     */
    public LegacyBookProvider(String bookId, PackOutput packOutput, String modId, ModonomiconLanguageProvider defaultLang, ModonomiconLanguageProvider... translations) {
        this(bookId, modId, defaultLang, translations);
    }

    /**
     * @param defaultLang The LanguageProvider to fill with this book provider. IMPORTANT: the Language Provider needs to be added to the DataGenerator AFTER the BookProvider.
     */
    public LegacyBookProvider(String bookId, String modId, ModonomiconLanguageProvider defaultLang, ModonomiconLanguageProvider... translations) {
        this(bookId, modId, defaultLang, makeLangMap(defaultLang, translations));
    }

    /**
     * @param defaultLang The LanguageProvider to fill with this book provider. IMPORTANT: the Language Provider needs to be added to the DataGenerator AFTER the BookProvider.
     */
    public LegacyBookProvider(String bookId, String modId, ModonomiconLanguageProvider defaultLang, Map<String, ModonomiconLanguageProvider> translations) {
        super(modId, defaultLang, translations, new BookContextHelper(modId), new ConditionHelper());
        this.book = null;

        this.bookId = bookId;
        this.currentSortIndex = 0;
    }

    public String bookId() {
        return this.bookId;
    }

    /**
     * Register a macro (= simple string.replace() of macro -> value) to be used in all category providers of this book.
     */
    protected void registerDefaultMacro(String macro, String value) {
        this.registerMacro(macro, value);
    }

    protected BookCategoryModel add(BookCategoryModel category) {
        if (category.getSortNumber() == -1) {
            category.withSortNumber(this.currentSortIndex++);
        }
        this.book.withCategory(category);
        return category;
    }

    @Override
    public void generate(BiConsumer<ResourceLocation, BookModel> consumer) {
        this.registerDefaultMacros();

        this.context().book(this.bookId());
        var book = this.generateBook();

        consumer.accept(this.book.getId(), this.book);
    }

    /**
     * Call registerMacro() here to make macros (= simple string.replace() of macro -> value) available to all category providers of this book.
     */
    protected abstract void registerDefaultMacros();

    /**
     * Override this to generate your book.
     * Each BookProvider should generate only one book.
     * Context already is set to the book id provided in the constructor.
     */
    protected abstract BookModel generateBook();
}