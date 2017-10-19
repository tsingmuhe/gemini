package com.sunchangpeng.gemini.zookeeper.watcher;

/**
 * Created by sunchangpeng
 */
public interface TreeListener {
    public void onAdd(String path, String data);

    public void onDelete(String path);

    public void onUpdate(String path, String newData);
}
