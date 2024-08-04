// SPDX-FileCopyrightText: 2024 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.modonomicon.events;

import com.klikli_dev.modonomicon.api.events.EntryClickedEvent;
import com.klikli_dev.modonomicon.api.events.EntryFirstReadEvent;
import com.klikli_dev.modonomicon.api.events.EventPriority;
import com.mojang.datafixers.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ModonomiconEvents {

    private static final Client client = new Client();

    private static final Server server = new Server();

    public static Client client() {
        return client;
    }

    public static Server server() {
        return server;
    }

    public static class Server {
        private final List<Pair<Consumer<EntryFirstReadEvent>, EventPriority>> entryFirstReadCallbacks = new ArrayList<>();

        /**
         * Register a callback for the EntryFirstReadEvent with Normal priority.
         * Forge: Should be called in FMLClientSetupEvent
         * Fabric: Should be called from ClientModInitializer
         */
        public void onEntryFirstRead(Consumer<EntryFirstReadEvent> callback) {
            this.onEntryFirstRead(callback, EventPriority.NORMAL);

        }

        /**
         * Register a callback for the EntryFirstReadEvent with the given priority.
         * Forge: Should be called in FMLClientSetupEvent
         * Fabric: Should be called from ClientModInitializer
         */
        public void onEntryFirstRead(Consumer<EntryFirstReadEvent> callback, EventPriority priority) {
            this.entryFirstReadCallbacks.add(Pair.of(callback, priority));
            this.entryFirstReadCallbacks.sort((a, b) -> b.getSecond().compareTo(a.getSecond()));
        }

        /**
         * Fires the entry clicked event.
         */
        public void entryFirstRead(EntryFirstReadEvent event) {
            for (var callback : this.entryFirstReadCallbacks) {
                callback.getFirst().accept(event);
            }
        }
    }

    public static class Client {

        private final List<Pair<Consumer<EntryClickedEvent>, EventPriority>> entryClickedCallbacks = new ArrayList<>();

        /**
         * Register a callback for the EntryClickedEvent with Normal priority.
         * Forge: Should be called in FMLClientSetupEvent
         * Fabric: Should be called from ClientModInitializer
         */
        public void onEntryClicked(Consumer<EntryClickedEvent> callback) {
            this.onEntryClicked(callback, EventPriority.NORMAL);

        }

        /**
         * Register a callback for the EntryClickedEvent with the given priority.
         * Forge: Should be called in FMLClientSetupEvent
         * Fabric: Should be called from ClientModInitializer
         */
        public void onEntryClicked(Consumer<EntryClickedEvent> callback, EventPriority priority) {
            this.entryClickedCallbacks.add(Pair.of(callback, priority));
            this.entryClickedCallbacks.sort((a, b) -> b.getSecond().compareTo(a.getSecond()));
        }

        /**
         * Fires the entry clicked event, and returns true if the event was canceled.
         */
        public boolean entryClicked(EntryClickedEvent event) {
            for (var callback : this.entryClickedCallbacks) {
                callback.getFirst().accept(event);
                if (event.isCanceled()) {
                    break;
                }
            }

            return event.isCanceled();
        }

        private final List<Pair<Consumer<EntryFirstReadEvent>, EventPriority>> entryFirstReadCallbacks = new ArrayList<>();

        /**
         * Register a callback for the EntryFirstReadEvent with Normal priority.
         * Forge: Should be called in FMLClientSetupEvent
         * Fabric: Should be called from ClientModInitializer
         */
        public void onEntryFirstRead(Consumer<EntryFirstReadEvent> callback) {
            this.onEntryFirstRead(callback, EventPriority.NORMAL);

        }

        /**
         * Register a callback for the EntryFirstReadEvent with the given priority.
         * Forge: Should be called in FMLClientSetupEvent
         * Fabric: Should be called from ClientModInitializer
         */
        public void onEntryFirstRead(Consumer<EntryFirstReadEvent> callback, EventPriority priority) {
            this.entryFirstReadCallbacks.add(Pair.of(callback, priority));
            this.entryFirstReadCallbacks.sort((a, b) -> b.getSecond().compareTo(a.getSecond()));
        }

        /**
         * Fires the entry clicked event.
         */
        public void entryFirstRead(EntryFirstReadEvent event) {
            for (var callback : this.entryFirstReadCallbacks) {
                callback.getFirst().accept(event);
            }
        }
    }

}
