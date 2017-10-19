package com.sunchangpeng.gemini.zookeeper;

import com.sunchangpeng.gemini.zookeeper.watcher.ChildListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sunchangpeng
 */
public class TestChildListener implements ChildListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestNodeListerner.class);

    @Override
    public void onAdd(String path, String data) {
        LOGGER.info("TestChildListener=======onAdd#########" + path + "########" + data);
    }

    @Override
    public void onDelete(String path) {
        LOGGER.info("TestChildListener=======onDelete#########" + path);
    }

    @Override
    public void onUpdate(String path, String newData) {
        LOGGER.info("TestChildListener=======onUpdate#########" + path + "########" + newData);
    }
}
