/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.modonomicon.client.gui.book.button;

import com.klikli_dev.modonomicon.book.BookCategory;
import com.klikli_dev.modonomicon.client.gui.BookGuiManager;
import com.klikli_dev.modonomicon.client.gui.book.node.BookParentNodeScreen;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;

public class CategoryButton extends Button {

    private final BookParentNodeScreen parent;
    private final BookCategory category;

    public CategoryButton(BookParentNodeScreen parent, BookCategory category, int pX, int pY, int width, int height, OnPress pOnPress, Tooltip tooltip) {
        super(pX, pY, width, height, Component.literal(""), pOnPress, Button.DEFAULT_NARRATION);
        this.setTooltip(tooltip);
        this.parent = parent;
        this.category = category;
    }

    public BookCategory getCategory() {
        return this.category;
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTicks) {
        if (this.visible) {
            guiGraphics.pose().pushPose();
            int xOffset = this.getCategory().getBook().getCategoryButtonXOffset();
            guiGraphics.pose().translate(xOffset, 0, 0);

            int texX = 0;
            int texY = 145;

            int renderX = this.getX();
            int renderWidth = this.width;

            if (BookGuiManager.get().openBookCategoryScreen != null && this.category == BookGuiManager.get().openBookCategoryScreen.getCategory()) {
                renderX -= 3;
                renderWidth += 3;
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            } else if (this.isHovered()) {
                renderX -= 1;
                renderWidth += 1;
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            } else {
                RenderSystem.setShaderColor(0.8f, 0.8f, 0.8f, 1.0F);
            }

            //draw category button background
//            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            guiGraphics.blit(this.parent.getBookOverviewTexture(), renderX, this.getY(), texX, texY, renderWidth, this.height, 256, 256);

            //then draw icon
            int iconSize = 16;
            int centerIconOffset = iconSize / 2;
            float scale = this.getCategory().getBook().getCategoryButtonIconScale();

            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(0, 0, 100); //push category icon to front
            guiGraphics.pose().translate(renderX + 8, this.getY() + 2, 0); //move to desired render location

            //now scale around center
            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(centerIconOffset, centerIconOffset, 0);
            guiGraphics.pose().scale(scale, scale, 1);
            guiGraphics.pose().translate(-centerIconOffset, -centerIconOffset, 0);

            this.category.getIcon().render(guiGraphics, 0, 0);

            guiGraphics.pose().popPose();

            guiGraphics.pose().popPose();

            guiGraphics.pose().popPose();
        }
    }
}
