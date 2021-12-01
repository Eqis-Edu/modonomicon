/*
 * LGPL-3-0
 *
 * Copyright (C) 2021 klikli-dev
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package com.klikli_dev.modonomicon.data.book.pages;

import com.google.gson.JsonObject;
import com.klikli_dev.modonomicon.api.data.book.BookPage;
import com.klikli_dev.modonomicon.util.BookGsonHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

public class BookTextPage implements BookPage {
    protected Component title;
    protected Component text;

    public BookTextPage(Component title, Component text) {
        this.title = title;
        this.text = text;
    }

    public static BookTextPage fromJson(JsonObject json) {
        //if it is a simple string, load as translatable component, otherwise deserialize full component
        Component title = BookGsonHelper.getAsComponent(json, "title", null);
        Component text = BookGsonHelper.getAsComponent(json, "text", null);
        return new BookTextPage(title, text);
    }

    public Component getTitle() {
        return this.title;
    }

    public Component getText() {
        return this.text;
    }
}