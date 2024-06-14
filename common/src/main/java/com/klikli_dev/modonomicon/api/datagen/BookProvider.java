/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */


package com.klikli_dev.modonomicon.api.datagen;

import com.klikli_dev.modonomicon.api.ModonomiconConstants;
import com.klikli_dev.modonomicon.api.datagen.book.BookCategoryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookCommandModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookModel;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringUtil;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * A book provider that can handle multiple books but provides only few convenience methods. Consider using {@link SingleBookProvider} instead!
 */
public abstract class BookProvider extends ModonomiconProviderBase implements DataProvider {

    protected final CompletableFuture<HolderLookup.Provider> registries;

    protected final PackOutput packOutput;
    //This is a bit of a relic, one provider is only supposed to generate one book.
    protected final Map<ResourceLocation, BookModel> bookModels;


    /**
     * @param defaultLang The LanguageProvider to fill with this book provider. IMPORTANT: the Language Provider needs to be added to the DataGenerator AFTER the BookProvider.
     */
    public BookProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries, String modId, ModonomiconLanguageProvider defaultLang, ModonomiconLanguageProvider... translations) {
        this(packOutput, registries, modId, defaultLang, makeLangMap(defaultLang, translations));
    }

    /**
     * @param defaultLang The LanguageProvider to fill with this book provider. IMPORTANT: the Language Provider needs to be added to the DataGenerator AFTER the BookProvider.
     */
    public BookProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries, String modId, ModonomiconLanguageProvider defaultLang, Map<String, ModonomiconLanguageProvider> translations) {
        super(modId, defaultLang, translations, new BookContextHelper(modId), new ConditionHelper());
        this.packOutput = packOutput;
        this.registries = registries;
        this.bookModels = new Object2ObjectOpenHashMap<>();
    }

    /**
     * Register a macro (= simple string.replace() of macro -> value) to be used in all category providers of this book.
     */
    protected void registerDefaultMacro(String macro, String value) {
        this.registerMacro(macro, value);
    }

    protected Path getPath(Path dataFolder, BookModel bookModel) {
        ResourceLocation id = bookModel.getId();
        return dataFolder
                .resolve(id.getNamespace())
                .resolve(ModonomiconConstants.Data.MODONOMICON_DATA_PATH)
                .resolve(id.getPath() + "/book.json");
    }

    protected Path getPath(Path dataFolder, BookCategoryModel bookCategoryModel) {
        ResourceLocation id = bookCategoryModel.getId();
        return dataFolder
                .resolve(id.getNamespace())
                .resolve(ModonomiconConstants.Data.MODONOMICON_DATA_PATH)
                .resolve(bookCategoryModel.getBook().getId().getPath())
                .resolve("categories")
                .resolve(id.getPath() + ".json");
    }

    protected Path getPath(Path dataFolder, BookCommandModel bookCommandModel) {
        ResourceLocation id = bookCommandModel.getId();
        return dataFolder
                .resolve(id.getNamespace())
                .resolve(ModonomiconConstants.Data.MODONOMICON_DATA_PATH)
                .resolve(bookCommandModel.getBook().getId().getPath())
                .resolve("commands")
                .resolve(id.getPath() + ".json");
    }

    protected Path getPath(Path dataFolder, BookEntryModel bookEntryModel) {
        ResourceLocation id = bookEntryModel.getId();
        return dataFolder
                .resolve(id.getNamespace())
                .resolve(ModonomiconConstants.Data.MODONOMICON_DATA_PATH)
                .resolve(bookEntryModel.getCategory().getBook().getId().getPath())
                .resolve("entries")
                .resolve(id.getPath() + ".json");
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        return this.registries.thenCompose(registries -> {
            List<CompletableFuture<?>> futures = new ArrayList<>();

            Path dataFolder = this.packOutput.getOutputFolder(PackOutput.Target.DATA_PACK);

            this.registerDefaultMacros();
            this.generate();

            for (var bookModel : this.bookModels.values()) {
                Path bookPath = this.getPath(dataFolder, bookModel);
                futures.add(DataProvider.saveStable(cache, bookModel.toJson(registries), bookPath));

                for (var bookCategoryModel : bookModel.getCategories()) {
                    Path bookCategoryPath = this.getPath(dataFolder, bookCategoryModel);
                    futures.add(DataProvider.saveStable(cache, bookCategoryModel.toJson(registries), bookCategoryPath));

                    for (var bookEntryModel : bookCategoryModel.getEntries()) {
                        Path bookEntryPath = this.getPath(dataFolder, bookEntryModel);
                        futures.add(DataProvider.saveStable(cache, bookEntryModel.toJson(registries), bookEntryPath));
                    }
                }

                for (var bookCommandModel : bookModel.getCommands()) {
                    Path bookCommandPath = this.getPath(dataFolder, bookCommandModel);
                    futures.add(DataProvider.saveStable(cache, bookCommandModel.toJson(registries), bookCommandPath));
                }
            }

            return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
        });
    }

    @Override
    public String getName() {
        return "Books: " + this.modId();
    }

    protected BookModel add(BookModel bookModel) {
        if (this.bookModels.containsKey(bookModel.getId()))
            throw new IllegalStateException("Duplicate book " + bookModel.getId());
        this.bookModels.put(bookModel.getId(), bookModel);
        return bookModel;
    }

    /**
     * Call registerMacro() here to make macros (= simple string.replace() of macro -> value) available to all category providers of this book.
     */
    protected abstract void registerDefaultMacros();

    /**
     * Override and generate books in here.
     * Consider using {@link SingleBookProvider} instead!
     */
    protected abstract void generate();
}
