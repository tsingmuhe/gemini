package com.sunchangpeng.gemini.scheduler.configuration.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;

/**
 * Created by sunchangpeng
 */
public class ZookeeperHealthIndicator extends AbstractHealthIndicator {
    private final CuratorFramework curator;

    public ZookeeperHealthIndicator(CuratorFramework curator) {
        this.curator = curator;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        try {

            if (this.curator.getState() != CuratorFrameworkState.STARTED) {
                builder.down().withDetail("error", "Client not started");
            } else if (this.curator.checkExists().forPath("/") == null) {
                builder.down().withDetail("error", "Root for namespace does not exist");
            } else {
                builder.up();
            }

            builder.withDetail("connectionString", this.curator.getZookeeperClient().getCurrentConnectionString())
                    .withDetail("state", this.curator.getState());
        } catch (Exception e) {
            builder.down(e);
        }
    }
}
