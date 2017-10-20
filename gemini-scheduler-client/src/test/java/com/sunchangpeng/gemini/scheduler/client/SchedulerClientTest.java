package com.sunchangpeng.gemini.scheduler.client;

import com.sunchangpeng.gemini.scheduler.client.config.SchedulerClientConfig;
import com.sunchangpeng.gemini.zookeeper.ZkPaths;
import com.sunchangpeng.gemini.zookeeper.ZkTemplate;
import org.apache.curator.framework.CuratorFramework;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

/**
 * Created by sunchangpeng
 */
public class SchedulerClientTest {
    private static CuratorFramework curatorFramework;
    private static ZkTemplate zkTemplate;
    private static SchedulerClient schedulerClient;

    @BeforeClass
    public static void beforeClass() {
        curatorFramework = ZkTemplateBuilder.curatorFramework();
        zkTemplate = ZkTemplateBuilder.zkTemplate(curatorFramework);
        SchedulerClientConfig clientConfig = new SchedulerClientConfig();
        ExecutorService executor = Executors.newExecutor(32, 10000, "JOB-EXECUTOR-");
        JobExecutor jobExecutor = new JobExecutor(executor);
        schedulerClient = new SchedulerClient(clientConfig, zkTemplate, jobExecutor);
    }

    @AfterClass
    public static void afterClass() throws IOException {
        schedulerClient.close();
        curatorFramework.close();
    }

    @Test
    public void test() throws InterruptedException {
        String jobInstanceNodePath = ZkPaths.pathOfJobInstance("sunchp", "com.sunchangpeng.gemini.scheduler.client.HelloJob", 1L);
        zkTemplate.deleteRecursivelyIfExists(jobInstanceNodePath);

        HelloJob job = new HelloJob();
        schedulerClient.registerJob(job);

        String result = zkTemplate.create(jobInstanceNodePath);

        Thread.sleep(Integer.MAX_VALUE);
    }
}