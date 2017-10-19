package com.sunchangpeng.gemini.zookeeper.watcher;

/**
 * Created by sunchangpeng
 */
public interface NodeListener {
    public void onUpdate(String newData);

    public void onDelete();
}
