package com.sunchangpeng.gemini.zookeeper.watcher;

/**
 * Created by sunchangpeng
 */
public interface TreeListener {
    void onAdd(String path, String data);

    void onDelete(String path);

    void onUpdate(String path, String newData);
}
