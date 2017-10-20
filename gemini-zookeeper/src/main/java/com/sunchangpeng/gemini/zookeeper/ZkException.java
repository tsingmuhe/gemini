package com.sunchangpeng.gemini.zookeeper;

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
}
