
package com.tag;

import java.util.HashMap;
import java.util.Map;

public class TagFactory {

    private static final Map<String, Tag> TAG_CACHE = new HashMap<>();

    public static Tag getTag(String name) {
        String key = name.trim().toLowerCase();
        TAG_CACHE.putIfAbsent(key, new Tag(key));
        return TAG_CACHE.get(key);
    }

    public static Map<String, Tag> allTags() {
        return TAG_CACHE;
    }
}
