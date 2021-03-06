package org.f0w.k2i.core.event;

/**
 * Indicates that movies import has started
 */
public final class ImportStartedEvent implements Event {
    public final int listSize;

    public ImportStartedEvent(int listSize) {
        this.listSize = listSize;
    }
}
