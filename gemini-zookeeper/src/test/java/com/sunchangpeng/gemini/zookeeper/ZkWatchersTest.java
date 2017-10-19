package com.sunchangpeng.gemini.zookeeper;

import com.sunchangpeng.gemini.zookeeper.utils.ZkPathUtil;
import com.sunchangpeng.gemini.zookeeper.watcher.ChildWatcher;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by sunchangpeng
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ZkWatchersTest {
    @Autowired
    private ZkClient zkClient;

    @Autowired
    private ZkWatchers zkWatchers;

    @Before
    public void setUp() throws Exception {
        zkClient.deleteRecursivelyIfExists(ZkPathUtil.format("junit"));
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void tree_watcher() {
        zkWatchers.createTreeWatcher(ZkPathUtil.format("junit"), new TestTreeListener());
        String result = zkClient.create(ZkPathUtil.format("junit", "sunchangpeng"));
        Stat stat = zkClient.update(result, "sunchangpeng1");
        zkClient.deleteRecursivelyIfExists(ZkPathUtil.format("junit", "sunchangpeng"));
    }

    @Test
    public void node_watcher() throws InterruptedException {
        zkWatchers.createNodeWatcher(ZkPathUtil.format("junit", "sunchangpeng"), new TestNodeListerner());
        String result = zkClient.create(ZkPathUtil.format("junit", "sunchangpeng"));
        Stat stat = zkClient.update(result, "sunchangpeng1");
    }

    @Test
    public void child_watcher() throws InterruptedException {
        ChildWatcher watcher = zkWatchers.createChildWatcher(ZkPathUtil.format("junit"), true, new TestChildListener());
        String result = zkClient.create(ZkPathUtil.format("junit", "sunchangpeng"));
        Stat stat = zkClient.update(result, "sunchangpeng1");
        Thread.sleep(Integer.MAX_VALUE);
    }
}