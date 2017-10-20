package com.sunchangpeng.gemini.zookeeper.utils;


/**
 * Created by sunchangpeng
 */
public abstract class PathUtil {
    public static String format(String... parts) {
        StringBuilder key = new StringBuilder();
        for (String part : parts) {
            key.append(sanitize(part));
        }
        return key.toString();
    }

    public static String sanitize(String path) {
        return withLeadingSlash(withoutSlashAtEnd(path));
    }

    private static String withLeadingSlash(String value) {
        return value.startsWith("/") ? value : "/" + value;
    }

    private static String withoutSlashAtEnd(String value) {
        return value.endsWith("/") ? value.substring(0, value.length() - 1) : value;
    }
}
