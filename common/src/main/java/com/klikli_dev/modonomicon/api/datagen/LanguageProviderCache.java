// SPDX-FileCopyrightText: 2024 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.modonomicon.api.datagen;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Supplier;

public class LanguageProviderCache implements ModonomiconLanguageProvider {

    private final String locale;
    private final Map<String, String> data = new Object2ObjectOpenHashMap<>();

    public LanguageProviderCache(String locale) {
        this.locale = locale;
    }

    public String locale() {
        return this.locale;
    }

    @Override
    public @NotNull Map<String, String> data() {
        return this.data;
    }

    @Override
    public void accept(String key, String value) {
        if (this.data.put(key, value) != null)
            throw new IllegalStateException("Duplicate translation key " + key);
    }
}
