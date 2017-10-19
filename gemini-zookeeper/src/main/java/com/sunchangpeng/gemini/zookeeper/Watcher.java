package com.sunchangpeng.gemini.zookeeper;

import java.io.Closeable;

/**
 * Created by sunchangpeng
 */
public interface Watcher extends Closeable {
    void watch();
}
