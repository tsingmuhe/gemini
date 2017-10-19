package com.sunchangpeng.gemini.zookeeper;

import com.sunchangpeng.gemini.zookeeper.watcher.NodeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sunchangpeng
 */
public class TestNodeListerner implements NodeListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestNodeListerner.class);
    @Override
    public void onUpdate(String newData) {
        LOGGER.info("TestNodeListerner=======onUpdate#########" + newData);
    }

    @Override
    public void onDelete() {
        LOGGER.info("TestNodeListerner=======onDelete");
    }
}
