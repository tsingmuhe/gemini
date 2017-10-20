package com.sunchangpeng.gemini.zookeeper.watcher;

import com.sunchangpeng.gemini.zookeeper.Watcher;
import com.sunchangpeng.gemini.zookeeper.ZkException;
import com.sunchangpeng.gemini.zookeeper.utils.StringUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.apache.curator.framework.recipes.cache.TreeCacheEvent.Type.*;

/**
 * Created by sunchangpeng
 */
public class TreeWatcher implements Watcher, TreeCacheListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(TreeWatcher.class);

    private final CuratorFramework curator;
    private final String path;
    private final TreeListener listener;
    private TreeCache treeCache;

    public TreeWatcher(CuratorFramework curator, String path, TreeListener listener) {
        this.curator = curator;
        this.path = path;
        this.listener = listener;
    }

    public void watch() {
        if (this.listener == null) {
            throw new ZkException("TreeListener for path " + this.path + " is required; it must not be null");
        }

        try {
            TreeCache cache = TreeCache.newBuilder(this.curator, this.path).build();
            cache.getListenable().addListener(this);
            cache.start();

            this.treeCache = cache;
        } catch (Exception e) {
            throw new ZkException("Error initializing TreeWatcher for path " + this.path, e);
        }
    }

    @Override
    public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
        TreeCacheEvent.Type eventType = event.getType();

        ChildData childData = event.getData();
        if (childData == null) {
            return;
        }

        String path = childData.getPath();

        if (eventType == NODE_ADDED) {
            this.listener.onAdd(path, StringUtil.getString(childData.getData()));
        } else if (eventType == NODE_REMOVED) {
            this.listener.onDelete(path);
        } else if (eventType == NODE_UPDATED) {
            this.listener.onUpdate(path, StringUtil.getString(childData.getData()));
        }
    }

    @Override
    public void close() throws IOException {
        if (this.treeCache != null) {
            this.treeCache.close();
            this.treeCache = null;
        }

        LOGGER.info("close TreeWatcher for path {} success", path);
    }

    public static TreeWatcher buildAndWatch(CuratorFramework curator, String path, TreeListener listener) {
        TreeWatcher watcher = new TreeWatcher(curator, path, listener);
        watcher.watch();
        LOGGER.info("build TreeWatcher for path {} success", path);
        return watcher;
    }
}
