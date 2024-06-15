package com.klikli_dev.modonomicon.api.datagen;

import com.klikli_dev.modonomicon.api.datagen.book.BookModel;
import net.minecraft.resources.ResourceLocation;

import java.util.function.BiConsumer;

public interface BookSubProvider {
    void generate(BiConsumer<ResourceLocation, BookModel> consumer);
}
