// SPDX-FileCopyrightText: 2024 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.modonomicon.datagen.book.demo.features;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.crafting.Ingredient;

public class SpotlightEntry extends EntryProvider {
    public static final String ID = "spotlight";

    public SpotlightEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );
        this.pageTitle("Spotlight Entry");
        this.pageText("""
                Spotlight pages allow to show items (actually, ingredients).
                """
        );

        this.page("spotlight1", () -> BookSpotlightPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .withItem(Ingredient.of(Items.APPLE))
        );
        this.pageTitle("Custom Title");
        this.pageText("""
                A sample spotlight page with custom title.\\
                It shows how an {0} can be rendered with a little border and fancyness around it.\\
                I am adding this link to see if spotlight pages have an offset problem. \\
                The entry needs to be longer to trigger scaling.\\
                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed sed odio eu dolor ornare tempor. In nibh erat, finibus sed eros id, finibus interdum justo. Aenean luctus, magna at pharetra accumsan, tellus libero tincidunt enim, id varius ante sem sit amet ipsum.\s
                """,
                this.itemLink(Items.APPLE)
        );

        this.page("spotlight2", () -> BookSpotlightPageModel.create()
                .withText(this.context().pageText())
                .withItem(Ingredient.of(Items.DIAMOND))
        );
        this.pageText("A sample spotlight page with automatic title.");

        var iconStack = new ItemStack(Items.LEATHER_HELMET);
        iconStack.set(DataComponents.DYED_COLOR, new DyedItemColor(0x169C9C, false));
        this.page("spotlight3", () -> BookSpotlightPageModel.create()
                .withText(this.context().pageText())
                .withItem(iconStack)
        );
        this.pageText("A sample spotlight page with an item with components");
    }

    @Override
    protected String entryName() {
        return "Spotlight Entry";
    }

    @Override
    protected String entryDescription() {
        return "An entry showcasing spotlight pages.";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.BEACON);
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
