package com.klikli_dev.modonomicon.datagen.book.demo.formatting;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.datagen.book.demo.FeaturesCategory;
import com.klikli_dev.modonomicon.datagen.book.demo.features.ConditionLevel1Entry;
import com.klikli_dev.modonomicon.datagen.book.demo.features.MultiblockEntry;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.item.Items;

public class LinkFormattingEntry extends EntryProvider {
    public static final String ID = "advanced";

    public LinkFormattingEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {

        this.page("page1", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("Http Links");
        this.pageText("""
                [Click me!](https://klikli-dev.github.io/modonomicon/) \\
                [Or me!](https://github.com/klikli-dev/modonomicon)
                """
        );


        this.page("page2", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("Book Links");
        this.pageText(STR."""
                \{this.entryLink("View a Multiblock", FeaturesCategory.ID, MultiblockEntry.ID)} \\
                \{this.entryLink("View a Condition", FeaturesCategory.ID, ConditionLevel1Entry.ID)} \\
                \{this.entryLink("View basic formatting", FeaturesCategory.ID, BasicFormattingEntry.ID)} \\
                [Link without book id](entry://formatting/basic) \\
                \{this.entryLink("Always locked", FeaturesCategory.ID, AlwaysLockedEntry.ID)} \\
                \{this.categoryLink("View Features Category", FeaturesCategory.ID)}
                """
        );

//        this.page("page3", () -> BookTextPageModel.create()
//                .withTitle(this.context().pageTitle())
//                .withText(this.context().pageText())
//        );
//        this.pageTitle("Patchouli Links");
//        this.pageText("""
//                [Link to a Patchouli Entry](patchouli://occultism:dictionary_of_spirits//misc/books_of_calling)
//                """
//        );
    }

    @Override
    protected String entryName() {
        return "Link Formatting Entry";
    }

    @Override
    protected String entryDescription() {
        return "An entry showcasing link formatting.";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return Pair.of(0, 2);
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.WRITABLE_BOOK);
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
