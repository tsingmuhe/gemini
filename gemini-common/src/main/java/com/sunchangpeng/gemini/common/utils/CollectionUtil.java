package com.sunchangpeng.gemini.common.utils;

import java.util.Collection;
import java.util.Map;

/**
 * Created by sunchangpeng
 */
public final class CollectionUtil {

    public static boolean isNullOrEmpty(Collection c){
        return c == null || c.isEmpty();
    }

    public static boolean isNullOrEmpty(Map<?, ?> m){
        return m == null || m.isEmpty();
    }
}
