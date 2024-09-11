/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.modonomicon.data;

import com.google.gson.JsonObject;
import com.klikli_dev.modonomicon.Modonomicon;
import com.klikli_dev.modonomicon.api.ModonomiconConstants;
import com.klikli_dev.modonomicon.api.ModonomiconConstants.Data.Condition;
import com.klikli_dev.modonomicon.api.ModonomiconConstants.Data.Page;
import com.klikli_dev.modonomicon.api.multiblock.Multiblock;
import com.klikli_dev.modonomicon.api.multiblock.StateMatcher;
import com.klikli_dev.modonomicon.api.multiblock.TriPredicate;
import com.klikli_dev.modonomicon.book.conditions.*;
import com.klikli_dev.modonomicon.book.entries.BookContentEntry;
import com.klikli_dev.modonomicon.book.entries.BookEntry;
import com.klikli_dev.modonomicon.book.entries.CategoryLinkBookEntry;
import com.klikli_dev.modonomicon.book.entries.EntryLinkBookEntry;
import com.klikli_dev.modonomicon.book.page.*;
import com.klikli_dev.modonomicon.multiblock.DenseMultiblock;
import com.klikli_dev.modonomicon.multiblock.SparseMultiblock;
import com.klikli_dev.modonomicon.multiblock.matcher.*;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;

public class LoaderRegistry {

    private static final Map<ResourceLocation, BookEntryJsonLoader<? extends BookEntry>> entryTypeJsonLoaders = Object2ObjectMaps.synchronize(new Object2ObjectOpenHashMap<>());
    private static final Map<ResourceLocation, NetworkLoader<? extends BookEntry>> entryTypeNetworkLoaders = Object2ObjectMaps.synchronize(new Object2ObjectOpenHashMap<>());

    private static final Map<ResourceLocation, JsonLoader<? extends BookPage>> pageJsonLoaders = Object2ObjectMaps.synchronize(new Object2ObjectOpenHashMap<>());
    private static final Map<ResourceLocation, NetworkLoader<? extends BookPage>> pageNetworkLoaders = Object2ObjectMaps.synchronize(new Object2ObjectOpenHashMap<>());

    private static final Map<ResourceLocation, JsonLoader<? extends BookCondition>> conditionJsonLoaders = Object2ObjectMaps.synchronize(new Object2ObjectOpenHashMap<>());

    private static final Map<ResourceLocation, NetworkLoader<? extends BookCondition>> conditionNetworkLoaders = Object2ObjectMaps.synchronize(new Object2ObjectOpenHashMap<>());
    private static final Map<ResourceLocation, JsonLoader<? extends Multiblock>> multiblockJsonLoaders = Object2ObjectMaps.synchronize(new Object2ObjectOpenHashMap<>());
    private static final Map<ResourceLocation, NetworkLoader<? extends Multiblock>> multiblockNetworkLoaders = Object2ObjectMaps.synchronize(new Object2ObjectOpenHashMap<>());

    private static final Map<ResourceLocation, JsonLoader<? extends StateMatcher>> stateMatcherJsonLoaders = Object2ObjectMaps.synchronize(new Object2ObjectOpenHashMap<>());
    private static final Map<ResourceLocation, NetworkLoader<? extends StateMatcher>> stateMatcherNetworkLoaders = Object2ObjectMaps.synchronize(new Object2ObjectOpenHashMap<>());


    private static final Map<ResourceLocation, TriPredicate<BlockGetter, BlockPos, BlockState>> predicates = Object2ObjectMaps.synchronize(new Object2ObjectOpenHashMap<>());

    /**
     * Call from common setup
     */
    public static void registerLoaders() {
        registerDefaultBookEntryTypes();
        registerDefaultPageLoaders();
        registerDefaultConditionLoaders();
        registerDefaultPredicates();
        registerDefaultStateMatcherLoaders();
        registerDefaultMultiblockLoaders();
    }

    private static void registerDefaultBookEntryTypes() {
        registerEntryType(ModonomiconConstants.Data.EntryType.CONTENT, BookContentEntry::fromJson, BookContentEntry::fromNetwork);
        registerEntryType(ModonomiconConstants.Data.EntryType.CATEGORY_LINK, CategoryLinkBookEntry::fromJson, CategoryLinkBookEntry::fromNetwork);
        registerEntryType(ModonomiconConstants.Data.EntryType.ENTRY_LINK, EntryLinkBookEntry::fromJson, EntryLinkBookEntry::fromNetwork);
    }

    private static void registerDefaultPageLoaders() {
        //TODO(BookPageLoading): when replacing jsonloader with bookpagejsonloader, remove the cast
        registerPageLoader(Page.TEXT, (BookPageJsonLoader<?>) BookTextPage::fromJson, BookTextPage::fromNetwork);
        registerPageLoader(Page.MULTIBLOCK, (BookPageJsonLoader<?>) BookMultiblockPage::fromJson, BookMultiblockPage::fromNetwork);
        registerPageLoader(Page.CRAFTING_RECIPE, (BookPageJsonLoader<?>) BookCraftingRecipePage::fromJson, BookCraftingRecipePage::fromNetwork);
        registerPageLoader(Page.SMELTING_RECIPE,(BookPageJsonLoader<?>)  BookSmeltingRecipePage::fromJson, BookSmeltingRecipePage::fromNetwork);
        registerPageLoader(Page.SMOKING_RECIPE, (BookPageJsonLoader<?>) BookSmokingRecipePage::fromJson, BookSmokingRecipePage::fromNetwork);
        registerPageLoader(Page.CAMPFIRE_COOKING_RECIPE, (BookPageJsonLoader<?>) BookCampfireCookingRecipePage::fromJson, BookCampfireCookingRecipePage::fromNetwork);
        registerPageLoader(Page.BLASTING_RECIPE, (BookPageJsonLoader<?>) BookBlastingRecipePage::fromJson, BookBlastingRecipePage::fromNetwork);
        registerPageLoader(Page.STONECUTTING_RECIPE, (BookPageJsonLoader<?>) BookStonecuttingRecipePage::fromJson, BookStonecuttingRecipePage::fromNetwork);
        registerPageLoader(Page.SMITHING_RECIPE, (BookPageJsonLoader<?>) BookSmithingRecipePage::fromJson, BookSmithingRecipePage::fromNetwork);
        registerPageLoader(Page.SPOTLIGHT,(BookPageJsonLoader<?>)  BookSpotlightPage::fromJson, BookSpotlightPage::fromNetwork);
        registerPageLoader(Page.EMPTY, (BookPageJsonLoader<?>) BookEmptyPage::fromJson, BookEmptyPage::fromNetwork);
        registerPageLoader(Page.ENTITY, (BookPageJsonLoader<?>) BookEntityPage::fromJson, BookEntityPage::fromNetwork);
        registerPageLoader(Page.IMAGE, (BookPageJsonLoader<?>) BookImagePage::fromJson, BookImagePage::fromNetwork);
    }

    private static void registerDefaultConditionLoaders() {
        registerConditionLoader(Condition.NONE, BookNoneCondition::fromJson, BookNoneCondition::fromNetwork);
        registerConditionLoader(Condition.ADVANCEMENT, BookAdvancementCondition::fromJson, BookAdvancementCondition::fromNetwork);
        registerConditionLoader(Condition.ENTRY_UNLOCKED, BookEntryUnlockedCondition::fromJson, BookEntryUnlockedCondition::fromNetwork);
        registerConditionLoader(Condition.ENTRY_READ, BookEntryReadCondition::fromJson, BookEntryReadCondition::fromNetwork);
        registerConditionLoader(Condition.OR, BookOrCondition::fromJson, BookOrCondition::fromNetwork);
        registerConditionLoader(Condition.AND, BookAndCondition::fromJson, BookAndCondition::fromNetwork);
        registerConditionLoader(Condition.TRUE, BookTrueCondition::fromJson, BookTrueCondition::fromNetwork);
        registerConditionLoader(Condition.FALSE, BookFalseCondition::fromJson, BookFalseCondition::fromNetwork);
        registerConditionLoader(Condition.MOD_LOADED, BookModLoadedCondition::fromJson, BookModLoadedCondition::fromNetwork);
        registerConditionLoader(Condition.CATEGORY_HAS_VISIBLE_ENTRIES, BookCategoryHasVisibleEntriesCondition::fromJson, BookCategoryHasVisibleEntriesCondition::fromNetwork);
    }

    private static void registerDefaultMultiblockLoaders() {
        registerMultiblockLoader(DenseMultiblock.TYPE, DenseMultiblock::fromJson, DenseMultiblock::fromNetwork);
        registerMultiblockLoader(SparseMultiblock.TYPE, SparseMultiblock::fromJson, SparseMultiblock::fromNetwork);
    }

    private static void registerDefaultStateMatcherLoaders() {
        registerStateMatcherLoader(AnyMatcher.TYPE, AnyMatcher::fromJson, AnyMatcher::fromNetwork);
        registerStateMatcherLoader(BlockMatcher.TYPE, BlockMatcher::fromJson, BlockMatcher::fromNetwork);
        registerStateMatcherLoader(BlockStateMatcher.TYPE, BlockStateMatcher::fromJson, BlockStateMatcher::fromNetwork);
        registerStateMatcherLoader(BlockStatePropertyMatcher.TYPE, BlockStatePropertyMatcher::fromJson, BlockStatePropertyMatcher::fromNetwork);
        registerStateMatcherLoader(DisplayOnlyMatcher.TYPE, DisplayOnlyMatcher::fromJson, DisplayOnlyMatcher::fromNetwork);
        registerStateMatcherLoader(PredicateMatcher.TYPE, PredicateMatcher::fromJson, PredicateMatcher::fromNetwork);
        registerStateMatcherLoader(TagMatcher.TYPE, TagMatcher::fromJson, TagMatcher::fromNetwork);
    }

    private static void registerDefaultPredicates() {
        registerPredicate(Matchers.AIR.getPredicateId(), (getter, pos, state) -> state.isAir());
        //noinspection deprecation
        registerPredicate(Modonomicon.loc("non_solid"), (getter, pos, state) -> !state.isSolid());
    }


    /**
     * Call from client setup
     */
    public static void registerEntryType(ResourceLocation id, BookEntryJsonLoader<? extends BookEntry> jsonLoader,
                                         NetworkLoader<? extends BookEntry> networkLoader) {
        entryTypeJsonLoaders.put(id, jsonLoader);
        entryTypeNetworkLoaders.put(id, networkLoader);
    }

    /**
     * Call from common setup
     */
    public static void registerPageLoader(ResourceLocation id, JsonLoader<? extends BookPage> jsonLoader,
                                          NetworkLoader<? extends BookPage> networkLoader) {
        pageJsonLoaders.put(id, jsonLoader);
        pageNetworkLoaders.put(id, networkLoader);
    }

    /**
     * Call from common setup
     */
    public static void registerConditionLoader(ResourceLocation id, JsonLoader<? extends BookCondition> jsonLoader,
                                               NetworkLoader<? extends BookCondition> networkLoader) {
        conditionJsonLoaders.put(id, jsonLoader);
        conditionNetworkLoaders.put(id, networkLoader);
    }

    /**
     * Call from common setup
     */
    public static void registerMultiblockLoader(ResourceLocation id, JsonLoader<? extends Multiblock> jsonLoader,
                                                NetworkLoader<? extends Multiblock> networkLoader) {
        multiblockJsonLoaders.put(id, jsonLoader);
        multiblockNetworkLoaders.put(id, networkLoader);
    }

    /**
     * Call from common setup
     */
    public static void registerStateMatcherLoader(ResourceLocation id, JsonLoader<? extends StateMatcher> jsonLoader,
                                                  NetworkLoader<? extends StateMatcher> networkLoader) {
        stateMatcherJsonLoaders.put(id, jsonLoader);
        stateMatcherNetworkLoaders.put(id, networkLoader);
    }

    /**
     * Call from common setup, so predicates are available on both sides.
     */
    public static void registerPredicate(ResourceLocation id, TriPredicate<BlockGetter, BlockPos, BlockState> predicate) {
        predicates.put(id, predicate);
    }

    public static BookEntryJsonLoader<? extends BookEntry> getEntryJsonLoader(ResourceLocation id) {
        var loader = entryTypeJsonLoaders.get(id);
        if (loader == null) {
            throw new IllegalArgumentException("No json loader registered for entry type " + id);
        }
        return loader;
    }

    public static NetworkLoader<? extends BookEntry> getEntryNetworkLoader(ResourceLocation id) {
        var loader = entryTypeNetworkLoaders.get(id);
        if (loader == null) {
            throw new IllegalArgumentException("No network loader registered for entry type " + id);
        }
        return loader;
    }

    public static JsonLoader<? extends StateMatcher> getStateMatcherJsonLoader(ResourceLocation id) {
        var loader = stateMatcherJsonLoaders.get(id);
        if (loader == null) {
            throw new IllegalArgumentException("No json loader registered for state matcher type " + id);
        }
        return loader;
    }

    public static NetworkLoader<? extends StateMatcher> getStateMatcherNetworkLoader(ResourceLocation id) {
        var loader = stateMatcherNetworkLoaders.get(id);
        if (loader == null) {
            throw new IllegalArgumentException("No network loader registered for state matcher type " + id);
        }
        return loader;
    }

    public static TriPredicate<BlockGetter, BlockPos, BlockState> getPredicate(ResourceLocation id) {
        var predicate = predicates.get(id);
        if (predicate == null) {
            throw new IllegalArgumentException("No predicated registered for id " + id);
        }
        return predicate;
    }

    public static JsonLoader<? extends BookPage> getPageJsonLoader(ResourceLocation id) {
        var loader = pageJsonLoaders.get(id);
        if (loader == null) {
            throw new IllegalArgumentException("No json loader registered for page type " + id);
        }
        return loader;
    }

    public static NetworkLoader<? extends BookPage> getPageNetworkLoader(ResourceLocation id) {
        var loader = pageNetworkLoaders.get(id);
        if (loader == null) {
            throw new IllegalArgumentException("No network loader registered for page type " + id);
        }
        return loader;
    }

    public static JsonLoader<? extends BookCondition> getConditionJsonLoader(ResourceLocation id) {
        var loader = conditionJsonLoaders.get(id);
        if (loader == null) {
            throw new IllegalArgumentException("No json loader registered for condition type " + id);
        }
        return loader;
    }

    public static NetworkLoader<? extends BookCondition> getConditionNetworkLoader(ResourceLocation id) {
        var loader = conditionNetworkLoaders.get(id);
        if (loader == null) {
            throw new IllegalArgumentException("No network loader registered for condition type " + id);
        }
        return loader;
    }

    public static JsonLoader<? extends Multiblock> getMultiblockJsonLoader(ResourceLocation id) {
        var loader = multiblockJsonLoaders.get(id);
        if (loader == null) {
            throw new IllegalArgumentException("No json loader registered for multiblock type " + id);
        }
        return loader;
    }

    public static NetworkLoader<? extends Multiblock> getMultiblockNetworkLoader(ResourceLocation id) {
        var loader = multiblockNetworkLoaders.get(id);
        if (loader == null) {
            throw new IllegalArgumentException("No network loader registered for multiblock type " + id);
        }
        return loader;
    }
}
