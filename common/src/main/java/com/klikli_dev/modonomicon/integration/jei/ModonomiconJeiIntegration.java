/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.modonomicon.integration.jei;

import net.minecraft.world.item.ItemStack;

public interface ModonomiconJeiIntegration {

    ModonomiconJeiIntegration instance = new ModonomiconJeiIntegrationImpl();

    static ModonomiconJeiIntegration get() {
        return instance;
    }

    boolean isLoaded();

    boolean isRecipesGuiOpen();

    void showRecipe(ItemStack stack);

    void showUses(ItemStack stack);
}
