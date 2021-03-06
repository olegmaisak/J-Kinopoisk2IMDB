package org.f0w.k2i.core.util;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class to reuse code working with streams and collections
 */
public final class CollectionUtils {
    private CollectionUtils() {
    }

    /**
     * Combines two lists into map, using first list as keys and second as values
     *
     * @param keys   List used as keys
     * @param values List used as values
     * @param <K>    Type of values in keys list
     * @param <V>    Type of values in values list
     * @return {@link LinkedHashMap}
     */
    public static <K, V> Map<K, V> combineLists(List<? extends K> keys, List<? extends V> values) {
        if (keys.size() != values.size()) {
            throw new IllegalArgumentException("Cannot combine lists with dissimilar sizes");
        }

        if (keys.isEmpty() && values.isEmpty()) {
            return Collections.emptyMap();
        }

        int size = values.size();

        Map<K, V> map = new LinkedHashMap<>(size);
        for (int i = 0; i < size; i++) {
            map.put(keys.get(i), values.get(i));
        }

        return map;
    }
}
