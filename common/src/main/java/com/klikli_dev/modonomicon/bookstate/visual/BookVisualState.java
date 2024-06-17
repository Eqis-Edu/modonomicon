/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.modonomicon.bookstate.visual;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

public class BookVisualState {
    public static final Codec<BookVisualState> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Codec.unboundedMap(ResourceLocation.CODEC, CategoryVisualState.CODEC).fieldOf("categoryStates").forGetter((state) -> state.categoryStates),
            ResourceLocation.CODEC.optionalFieldOf("openCategory").forGetter((state) -> Optional.ofNullable(state.openCategory)),
            Codec.INT.fieldOf("openPagesIndex").forGetter((state) -> state.openPagesIndex)
    ).apply(instance, BookVisualState::new));

    public Map<ResourceLocation, CategoryVisualState> categoryStates;

    @Nullable
    public ResourceLocation openCategory;

    /**
     * For books in index mode
     */
    public int openPagesIndex;

    public BookVisualState() {
        this(Object2ObjectMaps.emptyMap(), (ResourceLocation) null, 0);
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public BookVisualState(Map<ResourceLocation, CategoryVisualState> categoryStates, Optional<ResourceLocation> openCategory, int openPagesIndex) {
        this(categoryStates, openCategory.orElse(null), openPagesIndex);
    }

    public BookVisualState(Map<ResourceLocation, CategoryVisualState> categoryStates, @Nullable ResourceLocation openCategory, int openPagesIndex) {
        this.categoryStates = new Object2ObjectOpenHashMap<>(categoryStates);
        this.openCategory = openCategory;
        this.openPagesIndex = openPagesIndex;
    }
}
