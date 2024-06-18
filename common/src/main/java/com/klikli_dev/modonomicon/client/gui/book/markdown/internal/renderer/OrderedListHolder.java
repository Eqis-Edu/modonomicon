/*
 * SPDX-FileCopyrightText: 2015, Atlassian Pty Ltd
 *
 * SPDX-License-Identifier: BSD-2-Clause
 */

package com.klikli_dev.modonomicon.client.gui.book.markdown.internal.renderer;

import org.commonmark.node.OrderedList;

public class OrderedListHolder extends ListHolder {
    private final String delimiter;
    private int counter;

    public OrderedListHolder(ListHolder parent, OrderedList list) {
        super(parent);
        this.delimiter = list.getMarkerDelimiter() != null ? list.getMarkerDelimiter() : ".";
        this.counter = list.getMarkerStartNumber() != null ? list.getMarkerStartNumber() : 1;
    }

    public String getDelimiter() {
        return this.delimiter;
    }

    public int getCounter() {
        return this.counter;
    }

    public void increaseCounter() {
        this.counter++;
    }
}
