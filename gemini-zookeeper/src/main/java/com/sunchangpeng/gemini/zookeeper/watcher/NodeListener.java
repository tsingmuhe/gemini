package com.sunchangpeng.gemini.zookeeper.watcher;

/**
 * Created by sunchangpeng
 */
public interface NodeListener {
    void onUpdate(String newData);

    void onDelete();
}
