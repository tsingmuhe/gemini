package com.sunchangpeng.gemini.zookeeper;

import com.sunchangpeng.gemini.zookeeper.watcher.TreeListener;

/**
 * Created by sunchangpeng
 */
public class TestTreeListener implements TreeListener {
    @Override
    public void onAdd(String path, String data) {
        System.out.println("TestTreeListener========onAdd#########" + path + "#####" + data);
    }

    @Override
    public void onDelete(String path) {
        System.out.println("TestTreeListener========onDelete#########" + path);
    }

    @Override
    public void onUpdate(String path, String newData) {
        System.out.println("TestTreeListener========onUpdate#########" + path + "#####" + newData);
    }
}
