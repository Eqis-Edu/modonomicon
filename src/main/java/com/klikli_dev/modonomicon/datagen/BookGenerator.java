/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.modonomicon.datagen;

import com.klikli_dev.modonomicon.Modonomicon;
import com.klikli_dev.modonomicon.datagen.book.*;
import com.klikli_dev.modonomicon.datagen.book.condition.BookEntryReadCondition;
import com.klikli_dev.modonomicon.datagen.book.condition.BookEntryUnlockedCondition;
import com.klikli_dev.modonomicon.datagen.book.page.BookMultiblockPageModel;
import com.klikli_dev.modonomicon.datagen.book.page.BookTextPageModel;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookGenerator implements DataProvider {
    private final DataGenerator generator;
    private final Map<ResourceLocation, BookModel> bookModels;
    protected String modid;

    public BookGenerator(DataGenerator generator, String modid) {
        this.modid = modid;
        this.generator = generator;
        this.bookModels = new HashMap<>();
    }

    private static Path getPath(Path path, BookModel bookModel) {
        ResourceLocation id = bookModel.getId();
        return path.resolve("data/" + id.getNamespace() + "/modonomicons/" + id.getPath() + "/book.json");
    }

    private static Path getPath(Path path, BookCategoryModel bookCategoryModel) {
        ResourceLocation id = bookCategoryModel.getId();
        return path.resolve("data/" + id.getNamespace() +
                "/modonomicons/" + bookCategoryModel.getBook().getId().getPath() +
                "/categories/" + id.getPath() + ".json");
    }

    private static Path getPath(Path path, BookEntryModel bookEntryModel) {
        ResourceLocation id = bookEntryModel.getId();
        return path.resolve("data/" + id.getNamespace() +
                "/modonomicons/" + bookEntryModel.getCategory().getBook().getId().getPath() +
                "/entries/" + id.getPath() + ".json");
    }

    public ResourceLocation modLoc(String name) {
        return new ResourceLocation(this.modid, name);
    }

    private void start() {
        var demoBook = this.makeDemoBook();
        this.add(demoBook);
    }

    private BookModel makeDemoBook() {
        var helper = new BookLangHelper(this.modid);
        helper.book("demo");

        var featuresCategory = this.makeFeaturesCategory(helper);

        var demoBook = BookModel.builder()
                .withId(this.modLoc("demo"))
                .withName(helper.bookName())
                .withCategories(featuresCategory)
                .build();
        return demoBook;
    }

    private BookCategoryModel makeFeaturesCategory(BookLangHelper helper) {
        helper.category("features");

        var multiblockEntry = this.makeMultiblockEntry(helper);

        var conditionEntries = this.makeConditionEntries(helper);

        return BookCategoryModel.builder()
                .withId(this.modLoc("features"))
                .withName(helper.categoryName())
                .withIcon("minecraft:nether_star")
                .withEntries(multiblockEntry)
                .withEntries(conditionEntries)
                .build();
    }

    private BookEntryModel makeMultiblockEntry(BookLangHelper helper) {
        helper.entry("multiblock");

        helper.page("intro");
        var multiBlockIntroPage = BookTextPageModel.builder()
                .withText(helper.pageText())
                .withTitle(helper.pageTitle())
                .build();

        helper.page("preview");
        var multiblockPreviewPage = BookMultiblockPageModel.builder()
                .withMultiblockId(this.modLoc("blockentity"))
                .withMultiblockName("multiblocks.modonomicon.blockentity")
                .withText(helper.pageText())
                .build();

        return BookEntryModel.builder()
                .withId(this.modLoc("features/multiblock"))
                .withName(helper.entryName())
                .withDescription(helper.entryDescription())
                .withIcon("minecraft:furnace")
                .withX(0).withY(0)
                .withPages(multiBlockIntroPage, multiblockPreviewPage)
                .build();
    }

    private List<BookEntryModel> makeConditionEntries(BookLangHelper helper) {
        var result = new ArrayList<BookEntryModel>();

        helper.entry("condition_root");
        helper.page("info");
        var conditionRootEntryInfoPage = BookTextPageModel.builder()
                .withText(helper.pageText())
                .withTitle(helper.pageTitle())
                .build();
        var conditionRootEntry = BookEntryModel.builder()
                .withId(this.modLoc("features/condition_root"))
                .withName(helper.entryName())
                .withDescription(helper.entryDescription())
                .withIcon("minecraft:redstone_torch")
                .withX(3).withY(3)
                .withPages(conditionRootEntryInfoPage)
                .build();
        result.add(conditionRootEntry);

        helper.entry("condition_level_1");
        helper.page("info");
        var conditionLevel1EntryInfoPage = BookTextPageModel.builder()
                .withText(helper.pageText())
                .withTitle(helper.pageTitle())
                .build();
        var conditionLevel1EntryCondition = BookEntryReadCondition.builder()
                .withEntry(conditionRootEntry.getId())
                .build();
        var conditionLevel1Entry = BookEntryModel.builder()
                .withId(this.modLoc("features/condition_level_1"))
                .withName(helper.entryName())
                .withDescription(helper.entryDescription())
                .withIcon("minecraft:lever")
                .withX(6).withY(3)
                .withPages(conditionLevel1EntryInfoPage)
                .withCondition(conditionLevel1EntryCondition)
                .withParent(BookEntryParentModel.builder().withEntryId(conditionRootEntry.getId()).build())
                .build();
        result.add(conditionLevel1Entry);

        helper.entry("condition_level_2");
        helper.page("info");
        var conditionLevel2EntryInfoPage = BookTextPageModel.builder()
                .withText(helper.pageText())
                .withTitle(helper.pageTitle())
                .build();
        var conditionLevel2EntryCondition = BookEntryUnlockedCondition.builder()
                .withEntry(conditionLevel1Entry.getId())
                .build();
        var conditionLevel2Entry = BookEntryModel.builder()
                .withId(this.modLoc("features/condition_level_2"))
                .withName(helper.entryName())
                .withDescription(helper.entryDescription())
                .withIcon("minecraft:torch")
                .withX(6).withY(6)
                .withPages(conditionLevel2EntryInfoPage)
                .withCondition(conditionLevel2EntryCondition)
                .withParent(BookEntryParentModel.builder().withEntryId(conditionLevel1Entry.getId()).build())
                .build();
        result.add(conditionLevel2Entry);

        return result;
    }

    private BookModel add(BookModel bookModel) {
        if (this.bookModels.containsKey(bookModel.getId()))
            throw new IllegalStateException("Duplicate book " + bookModel.getId());
        this.bookModels.put(bookModel.getId(), bookModel);
        return bookModel;
    }

    @Override
    public void run(CachedOutput cache) throws IOException {
        Path folder = this.generator.getOutputFolder();
        this.start();

        for (var bookModel : this.bookModels.values()) {
            Path bookPath = getPath(folder, bookModel);
            try {
                DataProvider.saveStable(cache, bookModel.toJson(), bookPath);
            } catch (IOException exception) {
                Modonomicon.LOGGER.error("Couldn't save book {}", bookPath, exception);
            }

            for (var bookCategoryModel : bookModel.getCategories()) {
                Path bookCategoryPath = getPath(folder, bookCategoryModel);
                try {
                    DataProvider.saveStable(cache, bookCategoryModel.toJson(), bookCategoryPath);
                } catch (IOException exception) {
                    Modonomicon.LOGGER.error("Couldn't save book category {}", bookCategoryPath, exception);
                }

                for (var bookEntryModel : bookCategoryModel.getEntries()) {
                    Path bookEntryPath = getPath(folder, bookEntryModel);
                    try {
                        DataProvider.saveStable(cache, bookEntryModel.toJson(), bookEntryPath);
                    } catch (IOException exception) {
                        Modonomicon.LOGGER.error("Couldn't save book entry {}", bookEntryPath, exception);
                    }
                }
            }
        }
    }

    @Override
    public String getName() {
        return "Books: " + Modonomicon.MODID;
    }
}
