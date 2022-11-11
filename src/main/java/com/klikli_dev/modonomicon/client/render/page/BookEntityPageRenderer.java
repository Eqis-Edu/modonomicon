/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.modonomicon.client.render.page;

import com.klikli_dev.modonomicon.Modonomicon;
import com.klikli_dev.modonomicon.api.ModonomiconConstants.I18n.Gui;
import com.klikli_dev.modonomicon.book.page.BookEntityPage;
import com.klikli_dev.modonomicon.client.ClientTicks;
import com.klikli_dev.modonomicon.client.gui.book.BookContentScreen;
import com.klikli_dev.modonomicon.util.EntityUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class BookEntityPageRenderer extends BookPageRenderer<BookEntityPage> implements PageWithTextRenderer {
    private Entity entity;
    private boolean errored;
    private float renderScale;
    private float renderOffset;

    public BookEntityPageRenderer(BookEntityPage page) {
        super(page);
    }

    public static void renderEntity(PoseStack ms, Entity entity, Level world, float x, float y, float rotation, float renderScale, float offset) {
        entity.level = world;

        ms.pushPose();
        ms.translate(x, y, 50);
        ms.scale(renderScale, renderScale, renderScale);
        ms.translate(0, offset, 0);
        ms.mulPose(Vector3f.ZP.rotationDegrees(180));
        ms.mulPose(Vector3f.YP.rotationDegrees(rotation));
        EntityRenderDispatcher erd = Minecraft.getInstance().getEntityRenderDispatcher();
        MultiBufferSource.BufferSource immediate = Minecraft.getInstance().renderBuffers().bufferSource();
        erd.setRenderShadow(false);
        erd.render(entity, 0, 0, 0, 0, 1, ms, immediate, 0xF000F0);
        erd.setRenderShadow(true);
        immediate.endBatch();
        ms.popPose();
    }

    private void loadEntity(Level world) {
        if (!this.errored && (this.entity == null || !this.entity.isAlive())) {
            try {
                var entityLoader = EntityUtil.getEntityLoader(this.page.getEntityId());
                this.entity = entityLoader.apply(world);

                float width = this.entity.getBbWidth();
                float height = this.entity.getBbHeight();

                float entitySize = Math.max(1F, Math.max(width, height));

                this.renderScale = 100F / entitySize * 0.8F * this.getPage().getScale();
                this.renderOffset = Math.max(height, entitySize) * 0.5F + this.getPage().getOffset();
            } catch (Exception e) {
                this.errored = true;
                Modonomicon.LOGGER.error("Failed to load entity", e);
            }
        }
    }

    @Override
    public void onBeginDisplayPage(BookContentScreen parentScreen, int left, int top) {
        super.onBeginDisplayPage(parentScreen, left, top);

        this.loadEntity(parentScreen.getMinecraft().level);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float ticks) {
        if (!this.page.getEntityName().isEmpty()) {
            this.renderTitle(this.page.getEntityName(), false, poseStack, BookContentScreen.PAGE_WIDTH / 2, 0);
        }

        this.renderBookTextHolder(this.getPage().getText(), poseStack, 0, this.getTextY(), BookContentScreen.PAGE_WIDTH);

        int x = BookContentScreen.PAGE_WIDTH / 2 - 53;
        int y = 7;
        RenderSystem.enableBlend();
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
        BookContentScreen.drawFromTexture(poseStack, this.getPage().getBook(), x, y, 405, 149, 106, 106);

        if (this.errored) {
            this.font.drawShadow(poseStack, Component.translatable(Gui.PAGE_ENTITY_LOADING_ERROR), 58, 60, 0xFF0000);
        }

        if (this.entity != null) {
            float rotation = this.page.doesRotate() ? ClientTicks.total : this.page.getDefaultRotation();
            renderEntity(poseStack, this.entity, this.parentScreen.getMinecraft().level, 58, 60, rotation, this.renderScale, this.renderOffset);
        }

        var style = this.getClickedComponentStyleAt(mouseX, mouseY);
        if (style != null)
            this.parentScreen.renderComponentHoverEffect(poseStack, style, mouseX, mouseY);
    }

    @Nullable
    @Override
    public Style getClickedComponentStyleAt(double pMouseX, double pMouseY) {
        if (pMouseX > 0 && pMouseY > 0) {
            if (!this.page.getEntityName().isEmpty()) {
                var titleStyle = this.getClickedComponentStyleAtForTitle(this.page.getEntityName(), BookContentScreen.PAGE_WIDTH / 2, 0, pMouseX, pMouseY);
                if (titleStyle != null) {
                    return titleStyle;
                }
            }

            var textStyle = this.getClickedComponentStyleAtForTextHolder(this.page.getText(), 0, this.getTextY(), BookContentScreen.PAGE_WIDTH, pMouseX, pMouseY);
            if (textStyle != null) {
                return textStyle;
            }
        }
        return super.getClickedComponentStyleAt(pMouseX, pMouseY);
    }

    @Override
    public int getTextY() {
        return 115;
    }


}
