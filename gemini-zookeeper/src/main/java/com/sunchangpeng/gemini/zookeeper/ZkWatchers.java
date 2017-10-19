package com.sunchangpeng.gemini.zookeeper;

import com.sunchangpeng.gemini.zookeeper.watcher.*;
import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by sunchangpeng
 */
@Component
public class ZkWatchers {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZkWatchers.class);

    @Autowired
    private CuratorFramework curator;

    public TreeWatcher createTreeWatcher(String path, TreeListener listener) {
        return TreeWatcher.buildAndWatch(curator, path, listener);
    }

    public NodeWatcher createNodeWatcher(String path, NodeListener listener) {
        return NodeWatcher.buildAndWatch(curator, path, listener);
    }

    public ChildWatcher createChildWatcher(String path, boolean cacheData, ChildListener listener) {
        return ChildWatcher.buildAndWatch(curator, path, cacheData, listener);
    }
}
