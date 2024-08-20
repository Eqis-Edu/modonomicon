// SPDX-FileCopyrightText: 2024 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.modonomicon.integration.jei;

import net.minecraft.world.item.ItemStack;

public class ModonomiconJeiIntegrationDummy implements ModonomiconJeiIntegration {
    @Override
    public boolean isLoaded() {
        return false;
    }

    @Override
    public boolean isRecipesGuiOpen() {
        return false;
    }

    @Override
    public void showRecipe(ItemStack stack) {

    }

    @Override
    public void showUses(ItemStack stack) {

    }
}
