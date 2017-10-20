package com.sunchangpeng.gemini.zookeeper.watcher;

import com.sunchangpeng.gemini.zookeeper.Watcher;
import com.sunchangpeng.gemini.zookeeper.ZkException;
import com.sunchangpeng.gemini.zookeeper.utils.StringUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by sunchangpeng
 */
public class NodeWatcher implements Watcher, NodeCacheListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(NodeWatcher.class);

    private final CuratorFramework curator;
    private final String path;
    private final NodeListener listener;
    private NodeCache nodeCache;

    public NodeWatcher(CuratorFramework curator, String path, NodeListener listener) {
        this.curator = curator;
        this.path = path;
        this.listener = listener;
    }

    public void watch() {
        if (this.listener == null) {
            throw new ZkException("NodeListener for path " + this.path + " is required; it must not be null");
        }

        try {
            NodeCache cache = new NodeCache(this.curator, this.path);
            cache.getListenable().addListener(this);
            cache.start(true);

            this.nodeCache = cache;
        } catch (Exception e) {
            throw new ZkException("Error initializing NodeWatcher for path " + this.path, e);
        }
    }

    @Override
    public void nodeChanged() throws Exception {
        ChildData childData = this.nodeCache.getCurrentData();

        if (childData == null) {
            this.listener.onDelete();
            return;
        }

        this.listener.onUpdate(StringUtil.getString(childData.getData()));
    }

    @Override
    public void close() {
        if (this.nodeCache != null) {
            try {
                this.nodeCache.close();
            } catch (IOException e) {
                LOGGER.error("nodeCache close error", e);
            }

            this.nodeCache = null;
        }

        LOGGER.info("close NodeWatcher for path {} success", path);
    }

    public static NodeWatcher buildAndWatch(CuratorFramework curator, String path, NodeListener listener) {
        NodeWatcher watcher = new NodeWatcher(curator, path, listener);
        watcher.watch();
        LOGGER.info("build NodeWatcher for path {} success", path);
        return watcher;
    }
}
