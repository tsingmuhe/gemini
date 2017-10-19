package com.sunchangpeng.gemini.zookeeper;

import com.sunchangpeng.gemini.common.utils.StringFormat;

/**
 * Created by sunchangpeng
 */
public class ZkException extends RuntimeException {
    public ZkException() {
    }

    public ZkException(String message) {
        super(message);
    }

    public ZkException(Throwable cause) {
        super(cause);
    }

    public ZkException(String message, Throwable cause) {
        super(message, cause);
    }

    public ZkException(String format, Object... args) {
        super(StringFormat.format(format, args));
    }

    public ZkException(Throwable cause, String format, Object... args) {
        super(StringFormat.format(format, args), cause);
    }
}
