package com.sunchangpeng.gemini.common.utils;

import java.nio.charset.Charset;

/**
 * Created by sunchangpeng
 */
public abstract class StringUtil {
    public static final Charset UTF8 = Charset.forName("UTF-8");

    public static byte[] getByte(String str) {
        return str.getBytes(UTF8);
    }

    public static String getString(byte[] data) {
        if (data != null && data.length > 0) {
            return new String(data, UTF8);
        }

        return null;
    }
}
