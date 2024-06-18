/*
 * SPDX-FileCopyrightText: 2015, Atlassian Pty Ltd
 *
 * SPDX-License-Identifier: BSD-2-Clause
 */

package com.klikli_dev.modonomicon.client.gui.book.markdown.internal.renderer;

public abstract class ListHolder {
    private static final String INDENT_DEFAULT = "   ";
    private static final String INDENT_EMPTY = "";

    private final ListHolder parent;
    private final String indent;

    ListHolder(ListHolder parent) {
        this.parent = parent;

        if (parent != null) {
            this.indent = parent.indent + INDENT_DEFAULT;
        } else {
            this.indent = INDENT_EMPTY;
        }
    }

    public ListHolder getParent() {
        return this.parent;
    }

    public String getIndent() {
        return this.indent;
    }
}
