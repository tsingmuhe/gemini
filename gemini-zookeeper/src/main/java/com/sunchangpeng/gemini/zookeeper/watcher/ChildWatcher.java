package com.sunchangpeng.gemini.zookeeper.watcher;

import com.sunchangpeng.gemini.common.utils.StringUtil;
import com.sunchangpeng.gemini.zookeeper.Watcher;
import com.sunchangpeng.gemini.zookeeper.ZkException;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent.Type.*;

/**
 * Created by sunchangpeng
 */
public class ChildWatcher implements Watcher, PathChildrenCacheListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChildWatcher.class);

    private final CuratorFramework curator;
    private final String path;
    private final ChildListener listener;
    private final boolean cacheData;
    private PathChildrenCache pathChildrenCache;

    public ChildWatcher(CuratorFramework curator, String path, boolean cacheData, ChildListener listener) {
        this.curator = curator;
        this.path = path;
        this.listener = listener;
        this.cacheData = cacheData;
    }

    public void watch() {
        if (this.listener == null) {
            throw new ZkException("ChildListener for path {} is required; it must not be null", path);
        }

        try {
            PathChildrenCache cache = new PathChildrenCache(this.curator, this.path, this.cacheData);
            cache.getListenable().addListener(this);
            cache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);

            this.pathChildrenCache = cache;
        } catch (Exception e) {
            throw new ZkException(e, "Error initializing ChildWatcher for path {}", this.path);
        }
    }

    @Override
    public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
        PathChildrenCacheEvent.Type eventType = event.getType();
        ChildData childData = event.getData();
        if (childData == null) {
            return;
        }

        String path = childData.getPath();

        if (eventType == CHILD_ADDED) {
            this.listener.onAdd(path, StringUtil.getString(childData.getData()));
        } else if (eventType == CHILD_REMOVED) {
            this.listener.onDelete(path);
        } else if (eventType == CHILD_UPDATED) {
            this.listener.onUpdate(path, StringUtil.getString(childData.getData()));
        }
    }

    @Override
    public void close() throws IOException {
        if (this.pathChildrenCache != null) {
            this.pathChildrenCache.close();
            this.pathChildrenCache = null;
        }

        LOGGER.info("close ChildWatcher for path {} success", path);
    }


    public static ChildWatcher buildAndWatch(CuratorFramework curator, String path, boolean cacheData, ChildListener listener) {
        ChildWatcher watcher = new ChildWatcher(curator, path, cacheData, listener);
        watcher.watch();
        LOGGER.info("build ChildWatcher for path {} success", path);
        return watcher;
    }
}
