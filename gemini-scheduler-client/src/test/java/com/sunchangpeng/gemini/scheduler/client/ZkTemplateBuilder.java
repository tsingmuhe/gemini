package com.sunchangpeng.gemini.scheduler.client;

import com.sunchangpeng.gemini.zookeeper.ZkTemplate;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.TimeUnit;

/**
 * Created by sunchangpeng
 */
public class ZkTemplateBuilder {
    public static RetryPolicy exponentialBackoffRetry() {
        return new ExponentialBackoffRetry(50, 10, 500);
    }

    public static CuratorFramework curatorFramework() {
        try {
            RetryPolicy retryPolicy = exponentialBackoffRetry();

            CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder();
            builder.connectString("localhost:2181").namespace("gemini");
            CuratorFramework curator = builder.retryPolicy(retryPolicy).build();
            curator.start();
            curator.blockUntilConnected(10, TimeUnit.SECONDS);
            return curator;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static ZkTemplate zkTemplate(CuratorFramework curator) {
        return new ZkTemplate(curator);
    }
}
