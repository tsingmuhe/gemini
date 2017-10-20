package com.sunchangpeng.gemini.zookeeper;

import com.sunchangpeng.gemini.zookeeper.utils.PathUtil;
import com.sunchangpeng.gemini.zookeeper.utils.StringUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.data.Stat;
import org.junit.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sunchangpeng
 */
public class ZkTemplateTest {
    private static CuratorFramework curatorFramework;
    private static ZkTemplate zkTemplate;

    @BeforeClass
    public static void beforeClass() {
        curatorFramework = ZkTemplateBuilder.curatorFramework();
        zkTemplate = ZkTemplateBuilder.zkTemplate(curatorFramework);
    }

    @AfterClass
    public static void afterClass() {
        curatorFramework.close();
    }

    @Before
    public void setUp() throws Exception {
        zkTemplate.deleteRecursivelyIfExists(PathUtil.format("junit"));
    }

    //-----------------------PERSISTENT
    @Test
    public void create_null_data() {
        String result = zkTemplate.create(PathUtil.format("junit", "sunchangpeng"));
        Assert.assertEquals(PathUtil.format("junit", "sunchangpeng"), result);
    }

    @Test
    public void create_string_data() {
        String result = zkTemplate.create(PathUtil.format("junit", "sunchangpeng"), "sunchangpeng");
        Assert.assertEquals(PathUtil.format("junit", "sunchangpeng"), result);
    }

    @Test
    public void create_object_data() {
        Map<String, String> obj = new HashMap<>();
        obj.put("name", "value");
        String result = zkTemplate.createJson(PathUtil.format("junit", "sunchangpeng"), obj);
        Assert.assertEquals(PathUtil.format("junit", "sunchangpeng"), result);
    }

    //-----------------------PERSISTENT_SEQUENTIAL
    @Test
    public void create_sequential_null_data() {
        String result = zkTemplate.createSequential(PathUtil.format("junit", "sunchangpeng"));
        Assert.assertTrue(result.contains(PathUtil.format("junit", "sunchangpeng")));
    }


    @Test
    public void create_sequential_string_data() {
        String result = zkTemplate.createSequential(PathUtil.format("junit", "sunchangpeng"), "sunchangpeng");
        Assert.assertTrue(result.contains(PathUtil.format("junit", "sunchangpeng")));
    }

    @Test
    public void create_sequential_object_data() {
        Map<String, String> obj = new HashMap<>();
        obj.put("name", "value");
        String result = zkTemplate.createSequentialJson(PathUtil.format("junit", "sunchangpeng"), obj);
        Assert.assertTrue(result.contains(PathUtil.format("junit", "sunchangpeng")));
    }

    //-----------------------EPHEMERAL
    @Test
    public void create_ephemeral_null_data() {
        String result = zkTemplate.createEphemeral(PathUtil.format("junit", "sunchangpeng"));
        Assert.assertEquals(PathUtil.format("junit", "sunchangpeng"), result);
    }

    @Test
    public void create_ephemeral_string_data() {
        String result = zkTemplate.createEphemeral(PathUtil.format("junit", "sunchangpeng"), "sunchangpeng");
        Assert.assertEquals(PathUtil.format("junit", "sunchangpeng"), result);
    }

    @Test
    public void create_ephemeral_object_data() {
        Map<String, String> obj = new HashMap<>();
        obj.put("name", "value");
        String result = zkTemplate.createEphemeralJson(PathUtil.format("junit", "sunchangpeng"), obj);
        Assert.assertEquals(PathUtil.format("junit", "sunchangpeng"), result);
    }

    //-----------------------EPHEMERAL_SEQUENTIAL
    @Test
    public void create_ephemeral_sequential_null_data() {
        String result = zkTemplate.createEphemeralSequential(PathUtil.format("junit", "sunchangpeng"));
        Assert.assertTrue(result.contains(PathUtil.format("junit", "sunchangpeng")));
    }

    @Test
    public void create_ephemeral_sequential_string_data() {
        String result = zkTemplate.createEphemeralSequential(PathUtil.format("junit", "sunchangpeng"), "sunchangpeng");
        Assert.assertTrue(result.contains(PathUtil.format("junit", "sunchangpeng")));
    }

    @Test
    public void create_ephemeral_sequential_object_data() {
        Map<String, String> obj = new HashMap<>();
        obj.put("name", "value");
        String result = zkTemplate.createEphemeralSequentialJson(PathUtil.format("junit", "sunchangpeng"), obj);
        Assert.assertTrue(result.contains(PathUtil.format("junit", "sunchangpeng")));
    }

    //-----------------------checkExists
    @Test
    public void check_exists() {
        String result = zkTemplate.create(PathUtil.format("junit", "sunchangpeng"));
        boolean exists = zkTemplate.checkExists(result);
        Assert.assertTrue(exists);
    }

    @Test
    public void check_not_exists() {
        boolean exists = zkTemplate.checkExists(PathUtil.format("junit", "sunchangpeng"));
        Assert.assertFalse(exists);
    }

    //-----------------------update
    @Test
    public void update_null_data() {
        String result = zkTemplate.create(PathUtil.format("junit", "sunchangpeng"));
        Stat stat = zkTemplate.update(result);
        Assert.assertNotNull(stat);
    }

    @Test
    public void update_string_data() {
        String result = zkTemplate.create(PathUtil.format("junit", "sunchangpeng"));
        Stat stat = zkTemplate.update(result, "sunchangpeng1");
        Assert.assertNotNull(stat);
    }

    @Test
    public void update_object_data() {
        String result = zkTemplate.create(PathUtil.format("junit", "sunchangpeng"));
        Map<String, String> obj = new HashMap<>();
        obj.put("name", "value");
        Stat stat = zkTemplate.updateJson(result, obj);
        Assert.assertNotNull(stat);
    }

    //-----------------------delete
    @Test
    public void delete_recursively() {
        zkTemplate.deleteRecursivelyIfExists(PathUtil.format("junit", "sunchangpeng"));
    }

    //-----------------------get
    @Test
    public void get_byte_data() {
        String result = zkTemplate.create(PathUtil.format("junit", "sunchangpeng"), "sunchangpeng");
        byte[] data = zkTemplate.get(result);
        Assert.assertNotNull(StringUtil.getString(data), "sunchangpeng");
    }

    @Test
    public void get_string_data() {
        String result = zkTemplate.create(PathUtil.format("junit", "sunchangpeng"), "sunchangpeng");
        String data = zkTemplate.getString(result);
        Assert.assertNotNull(data, "sunchangpeng");
    }

    @Test
    public void get_obejct_data() {
        Map<String, String> obj = new HashMap<>();
        obj.put("name", "value");

        String result = zkTemplate.createJson(PathUtil.format("junit", "sunchangpeng"), obj);

        Map<String, String> data = zkTemplate.getObject(result, Map.class);
        Assert.assertNotNull(data.get("name"), "value");
    }

    //-----------------------getChildren
    @Test
    public void get_null_children1() {
        List<String> children = zkTemplate.getChildren(PathUtil.format("junit", "sunchangpeng"));
        Assert.assertTrue(children.isEmpty());
    }

    @Test
    public void get_null_children2() {
        String result = zkTemplate.create(PathUtil.format("junit", "sunchangpeng"), "sunchangpeng");
        List<String> children = zkTemplate.getChildren(PathUtil.format("junit", "sunchangpeng"));
        Assert.assertTrue(children.isEmpty());
    }

    @Test
    public void get_children() {
        String result = zkTemplate.create(PathUtil.format("junit", "sunchangpeng", "age"), "sunchangpeng");
        List<String> children = zkTemplate.getChildren(PathUtil.format("junit", "sunchangpeng"));
        Assert.assertTrue(children.get(0).equals("age"));
    }

    //-----------------------ensurePath
    @Test
    public void ensurePath() {
        zkTemplate.ensurePath("/x/xhh");
    }

    @Test
    public void tree_watcher() {
        zkTemplate.createTreeWatcher(PathUtil.format("junit"), new TestTreeListener());
        String result = zkTemplate.create(PathUtil.format("junit", "sunchangpeng"));
        Stat stat = zkTemplate.update(result, "sunchangpeng1");
        zkTemplate.deleteRecursivelyIfExists(PathUtil.format("junit", "sunchangpeng"));
    }

    @Test
    public void node_watcher() throws InterruptedException {
        zkTemplate.createNodeWatcher(PathUtil.format("junit", "sunchangpeng"), new TestNodeListerner());
        String result = zkTemplate.create(PathUtil.format("junit", "sunchangpeng"));
        Stat stat = zkTemplate.update(result, "sunchangpeng1");
    }

    @Test
    public void child_watcher() throws InterruptedException {
        zkTemplate.createChildWatcher(PathUtil.format("junit"), true, new TestChildListener());
        String result = zkTemplate.create(PathUtil.format("junit", "sunchangpeng"));
        Stat stat = zkTemplate.update(result, "sunchangpeng1");
    }
}