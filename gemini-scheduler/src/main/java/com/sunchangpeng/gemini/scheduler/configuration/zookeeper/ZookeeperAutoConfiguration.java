package com.sunchangpeng.gemini.scheduler.configuration.zookeeper;

import com.sunchangpeng.gemini.zookeeper.ZkTemplate;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by sunchangpeng
 */
@Configuration
@EnableConfigurationProperties(ZookeeperProperties.class)
public class ZookeeperAutoConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperAutoConfiguration.class);

    private final ZookeeperProperties properties;

    public ZookeeperAutoConfiguration(ZookeeperProperties properties) {
        this.properties = properties;
    }

    @Bean
    public RetryPolicy exponentialBackoffRetry() {
        return new ExponentialBackoffRetry(properties.getBaseSleepTimeMs(), properties.getMaxRetries(), properties.getMaxSleepMs());
    }

    @Bean(destroyMethod = "close")
    public CuratorFramework curatorFramework(RetryPolicy retryPolicy) throws Exception {
        CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder();
        builder.connectString(properties.getConnectString()).namespace(properties.getNamespace());
        CuratorFramework curator = builder.retryPolicy(retryPolicy).build();
        curator.start();

        LOGGER.info("blocking until connected to zookeeper for " + properties.getBlockUntilConnectedWait() + properties.getBlockUntilConnectedUnit());
        curator.blockUntilConnected(properties.getBlockUntilConnectedWait(), properties.getBlockUntilConnectedUnit());
        LOGGER.info("connected to zookeeper");

        return curator;
    }

    @Bean
    public ZkTemplate zkTemplate(CuratorFramework curator) {
        ZkTemplate bean = new ZkTemplate(curator);
        return bean;
    }

    @Bean
    public ZookeeperHealthIndicator zookeeperHealthIndicator(CuratorFramework curator) {
        return new ZookeeperHealthIndicator(curator);
    }
}
