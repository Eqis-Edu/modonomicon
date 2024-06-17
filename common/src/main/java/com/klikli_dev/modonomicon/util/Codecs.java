/*
 * SPDX-FileCopyrightText: 2023 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.modonomicon.util;

import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Codecs {
    public static final Codec<UUID> UUID = Codec.STRING.xmap(java.util.UUID::fromString, java.util.UUID::toString);

    public static <V> Codec<Set<V>> set(Codec<V> elementCodec) {
        return setFromList(elementCodec.listOf());
    }

    public static <V> Codec<Set<V>> setFromList(Codec<List<V>> listCodec) {
        return listCodec.xmap(ObjectOpenHashSet::new, ArrayList::new);
    }

}
