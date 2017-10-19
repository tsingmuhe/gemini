package com.sunchangpeng.gemini.zookeeper;

import com.sunchangpeng.gemini.common.utils.StringUtil;
import com.sunchangpeng.gemini.zookeeper.utils.ZkPathUtil;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sunchangpeng
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ZkManagerTest {
    @Autowired
    private ZkClient zkManager;

    @Before
    public void setUp() throws Exception {
        zkManager.deleteRecursivelyIfExists(ZkPathUtil.format("junit"));
    }

    @After
    public void tearDown() throws Exception {
    }

    //-----------------------PERSISTENT
    @Test
    public void create_null_data() {
        String result = zkManager.create(ZkPathUtil.format("junit", "sunchangpeng"));
        Assert.assertEquals(ZkPathUtil.format("junit", "sunchangpeng"), result);
    }

    @Test
    public void create_string_data() {
        String result = zkManager.create(ZkPathUtil.format("junit", "sunchangpeng"), "sunchangpeng");
        Assert.assertEquals(ZkPathUtil.format("junit", "sunchangpeng"), result);
    }

    @Test
    public void create_object_data() {
        Map<String, String> obj = new HashMap<>();
        obj.put("name", "value");
        String result = zkManager.createJson(ZkPathUtil.format("junit", "sunchangpeng"), obj);
        Assert.assertEquals(ZkPathUtil.format("junit", "sunchangpeng"), result);
    }

    //-----------------------PERSISTENT_SEQUENTIAL
    @Test
    public void create_sequential_null_data() {
        String result = zkManager.createSequential(ZkPathUtil.format("junit", "sunchangpeng"));
        Assert.assertTrue(result.contains(ZkPathUtil.format("junit", "sunchangpeng")));
    }


    @Test
    public void create_sequential_string_data() {
        String result = zkManager.createSequential(ZkPathUtil.format("junit", "sunchangpeng"), "sunchangpeng");
        Assert.assertTrue(result.contains(ZkPathUtil.format("junit", "sunchangpeng")));
    }

    @Test
    public void create_sequential_object_data() {
        Map<String, String> obj = new HashMap<>();
        obj.put("name", "value");
        String result = zkManager.createSequentialJson(ZkPathUtil.format("junit", "sunchangpeng"), obj);
        Assert.assertTrue(result.contains(ZkPathUtil.format("junit", "sunchangpeng")));
    }

    //-----------------------EPHEMERAL
    @Test
    public void create_ephemeral_null_data() {
        String result = zkManager.createEphemeral(ZkPathUtil.format("junit", "sunchangpeng"));
        Assert.assertEquals(ZkPathUtil.format("junit", "sunchangpeng"), result);
    }

    @Test
    public void create_ephemeral_string_data() {
        String result = zkManager.createEphemeral(ZkPathUtil.format("junit", "sunchangpeng"), "sunchangpeng");
        Assert.assertEquals(ZkPathUtil.format("junit", "sunchangpeng"), result);
    }

    @Test
    public void create_ephemeral_object_data() {
        Map<String, String> obj = new HashMap<>();
        obj.put("name", "value");
        String result = zkManager.createEphemeralJson(ZkPathUtil.format("junit", "sunchangpeng"), obj);
        Assert.assertEquals(ZkPathUtil.format("junit", "sunchangpeng"), result);
    }

    //-----------------------EPHEMERAL_SEQUENTIAL
    @Test
    public void create_ephemeral_sequential_null_data() {
        String result = zkManager.createEphemeralSequential(ZkPathUtil.format("junit", "sunchangpeng"));
        Assert.assertTrue(result.contains(ZkPathUtil.format("junit", "sunchangpeng")));
    }

    @Test
    public void create_ephemeral_sequential_string_data() {
        String result = zkManager.createEphemeralSequential(ZkPathUtil.format("junit", "sunchangpeng"), "sunchangpeng");
        Assert.assertTrue(result.contains(ZkPathUtil.format("junit", "sunchangpeng")));
    }

    @Test
    public void create_ephemeral_sequential_object_data() {
        Map<String, String> obj = new HashMap<>();
        obj.put("name", "value");
        String result = zkManager.createEphemeralSequentialJson(ZkPathUtil.format("junit", "sunchangpeng"), obj);
        Assert.assertTrue(result.contains(ZkPathUtil.format("junit", "sunchangpeng")));
    }

    //-----------------------checkExists
    @Test
    public void check_exists() {
        String result = zkManager.create(ZkPathUtil.format("junit", "sunchangpeng"));
        boolean exists = zkManager.checkExists(result);
        Assert.assertTrue(exists);
    }

    @Test
    public void check_not_exists() {
        boolean exists = zkManager.checkExists(ZkPathUtil.format("junit", "sunchangpeng"));
        Assert.assertFalse(exists);
    }

    //-----------------------update
    @Test
    public void update_null_data() {
        String result = zkManager.create(ZkPathUtil.format("junit", "sunchangpeng"));
        Stat stat = zkManager.update(result);
        Assert.assertNotNull(stat);
    }

    @Test
    public void update_string_data() {
        String result = zkManager.create(ZkPathUtil.format("junit", "sunchangpeng"));
        Stat stat = zkManager.update(result, "sunchangpeng1");
        Assert.assertNotNull(stat);
    }

    @Test
    public void update_object_data() {
        String result = zkManager.create(ZkPathUtil.format("junit", "sunchangpeng"));
        Map<String, String> obj = new HashMap<>();
        obj.put("name", "value");
        Stat stat = zkManager.updateJson(result, obj);
        Assert.assertNotNull(stat);
    }

    //-----------------------delete
    @Test
    public void delete_recursively() {
        zkManager.deleteRecursivelyIfExists(ZkPathUtil.format("junit", "sunchangpeng"));
    }

    //-----------------------get
    @Test
    public void get_byte_data() {
        String result = zkManager.create(ZkPathUtil.format("junit", "sunchangpeng"), "sunchangpeng");
        byte[] data = zkManager.get(result);
        Assert.assertNotNull(StringUtil.getString(data), "sunchangpeng");
    }

    @Test
    public void get_string_data() {
        String result = zkManager.create(ZkPathUtil.format("junit", "sunchangpeng"), "sunchangpeng");
        String data = zkManager.getString(result);
        Assert.assertNotNull(data, "sunchangpeng");
    }

    @Test
    public void get_obejct_data() {
        Map<String, String> obj = new HashMap<>();
        obj.put("name", "value");

        String result = zkManager.createJson(ZkPathUtil.format("junit", "sunchangpeng"), obj);

        Map<String, String> data = zkManager.getObject(result, Map.class);
        Assert.assertNotNull(data.get("name"), "value");
    }

    //-----------------------getChildren
    @Test
    public void get_null_children1() {
        List<String> children = zkManager.getChildren(ZkPathUtil.format("junit", "sunchangpeng"));
        Assert.assertTrue(children.isEmpty());
    }

    @Test
    public void get_null_children2() {
        String result = zkManager.create(ZkPathUtil.format("junit", "sunchangpeng"), "sunchangpeng");
        List<String> children = zkManager.getChildren(ZkPathUtil.format("junit", "sunchangpeng"));
        Assert.assertTrue(children.isEmpty());
    }

    @Test
    public void get_children() {
        String result = zkManager.create(ZkPathUtil.format("junit", "sunchangpeng", "age"), "sunchangpeng");
        List<String> children = zkManager.getChildren(ZkPathUtil.format("junit", "sunchangpeng"));
        Assert.assertTrue(children.get(0).equals("age"));
    }

    //-----------------------ensurePath
    @Test
    public void ensurePath() {
        zkManager.ensurePath("/x/xhh");
    }
}