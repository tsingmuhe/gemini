package com.sunchangpeng.gemini.zookeeper;

import com.alibaba.fastjson.JSON;
import com.sunchangpeng.gemini.common.utils.StringUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Created by sunchangpeng
 */
@Component
public class ZkClient {
    @Autowired
    private CuratorFramework curator;

    public String create(String path, byte[] data) {
        try {
            return curator.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path, data);
        } catch (Exception e) {
            throw new ZkException(e);
        }
    }

    public String create(String path) {
        return create(path, (byte[]) null);
    }

    public String create(String path, String data) {
        return create(path, StringUtil.getByte(data));
    }

    public String createJson(String path, Object obj) {
        return create(path, JSON.toJSONString(obj));
    }

    public String createSequential(String path, byte[] data) {
        try {
            return curator.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath(path, data);
        } catch (Exception e) {
            throw new ZkException(e);
        }
    }

    public String createSequential(String path) {
        return createSequential(path, (byte[]) null);
    }

    public String createSequential(String path, String data) {
        return createSequential(path, StringUtil.getByte(data));
    }

    public String createSequentialJson(String path, Object obj) {
        return createSequential(path, JSON.toJSONString(obj));
    }

    public String createEphemeral(String path, byte[] data) {
        try {
            return curator.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path, data);
        } catch (Exception e) {
            throw new ZkException(e);
        }
    }

    public String createEphemeral(String path) {
        return createEphemeral(path, (byte[]) null);
    }

    public String createEphemeral(String path, String data) {
        return createEphemeral(path, StringUtil.getByte(data));
    }

    public String createEphemeralJson(String path, Object obj) {
        return createEphemeral(path, JSON.toJSONString(obj));
    }

    public String createEphemeralSequential(String path, byte[] data) {
        try {
            return curator.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(path, data);
        } catch (Exception e) {
            throw new ZkException(e);
        }
    }

    public String createEphemeralSequential(String path) {
        return createEphemeralSequential(path, (byte[]) null);
    }

    public String createEphemeralSequential(String path, String data) {
        return createEphemeralSequential(path, StringUtil.getByte(data));
    }

    public String createEphemeralSequentialJson(String path, Object obj) {
        return createEphemeralSequential(path, JSON.toJSONString(obj));
    }

    public Boolean checkExists(String path) {
        try {
            Stat pathStat = curator.checkExists().forPath(path);
            return pathStat != null;
        } catch (Exception e) {
            throw new ZkException(e);
        }
    }

    public Stat update(String path, byte[] data) {
        try {
            return curator.setData().forPath(path, data);
        } catch (Exception e) {
            throw new ZkException(e);
        }
    }

    public Stat update(String path) {
        return update(path, (byte[]) null);
    }

    public Stat update(String path, String data) {
        return update(path, StringUtil.getByte(data));
    }

    public Stat updateJson(String path, Object data) {
        return update(path, JSON.toJSONString(data));
    }

    public void deleteRecursively(String path) {
        try {
            curator.delete().deletingChildrenIfNeeded().forPath(path);
        } catch (Exception e) {
            throw new ZkException(e);
        }
    }

    public void deleteRecursivelyIfExists(String path) {
        if (checkExists(path)) {
            deleteRecursively(path);
        }
    }

    public byte[] get(String path) {
        try {
            return curator.getData().forPath(path);
        } catch (Exception e) {
            throw new ZkException(e);
        }
    }

    public String getString(String path) {
        byte[] data = get(path);

        if (data != null) {
            return StringUtil.getString(data);
        }

        return null;
    }

    public <T> T getObject(String path, Class<T> clazz) {
        byte[] data = get(path);

        if (data != null) {
            return JSON.parseObject(StringUtil.getString(data), clazz);
        }

        return null;
    }

    public List<String> getChildren(String path) {
        try {
            if (!checkExists(path)) {
                return Collections.emptyList();
            }

            return curator.getChildren().forPath(path);
        } catch (Exception e) {
            throw new ZkException(e);
        }
    }

    public void ensurePath(String path) {
        try {
            curator.createContainers(path);
        } catch (Exception e) {
            throw new ZkException(e);
        }
    }
}
