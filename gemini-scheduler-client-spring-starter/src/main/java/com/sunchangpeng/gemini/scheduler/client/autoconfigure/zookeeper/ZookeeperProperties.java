package com.sunchangpeng.gemini.scheduler.client.autoconfigure.zookeeper;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.concurrent.TimeUnit;

/**
 * Created by sunchangpeng
 */
@ConfigurationProperties("spring.zookeeper")
public class ZookeeperProperties {
    /**
     * Connection string to the Zookeeper cluster
     */
    private String connectString = "localhost:2181";

    private String namespace = "gemini";

    /**
     * Is Zookeeper enabled
     */
    private boolean enabled = true;

    /**
     * Initial amount of time to wait between retries
     */
    private Integer baseSleepTimeMs = 50;

    /**
     * Max number of times to retry
     */
    private Integer maxRetries = 10;

    /**
     * Max time in ms to sleep on each retry
     */
    private Integer maxSleepMs = 500;

    /**
     * Wait time to block on connection to Zookeeper
     */
    private Integer blockUntilConnectedWait = 10;

    /**
     * The unit of time related to blocking on connection to Zookeeper
     */
    private TimeUnit blockUntilConnectedUnit = TimeUnit.SECONDS;

    public String getConnectString() {
        return this.connectString;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public Integer getBaseSleepTimeMs() {
        return this.baseSleepTimeMs;
    }

    public Integer getMaxRetries() {
        return this.maxRetries;
    }

    public Integer getMaxSleepMs() {
        return this.maxSleepMs;
    }

    public Integer getBlockUntilConnectedWait() {
        return this.blockUntilConnectedWait;
    }

    public TimeUnit getBlockUntilConnectedUnit() {
        return this.blockUntilConnectedUnit;
    }

    public void setConnectString(String connectString) {
        this.connectString = connectString;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setBaseSleepTimeMs(Integer baseSleepTimeMs) {
        this.baseSleepTimeMs = baseSleepTimeMs;
    }

    public void setMaxRetries(Integer maxRetries) {
        this.maxRetries = maxRetries;
    }

    public void setMaxSleepMs(Integer maxSleepMs) {
        this.maxSleepMs = maxSleepMs;
    }

    public void setBlockUntilConnectedWait(Integer blockUntilConnectedWait) {
        this.blockUntilConnectedWait = blockUntilConnectedWait;
    }

    public void setBlockUntilConnectedUnit(TimeUnit blockUntilConnectedUnit) {
        this.blockUntilConnectedUnit = blockUntilConnectedUnit;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }
}
