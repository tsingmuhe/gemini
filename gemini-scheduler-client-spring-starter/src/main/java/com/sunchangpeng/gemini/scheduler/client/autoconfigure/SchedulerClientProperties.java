package com.sunchangpeng.gemini.scheduler.client.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by sunchangpeng
 */
@ConfigurationProperties("gemini")
public class SchedulerClientProperties {

    private String appName;

    private String zkNamespace = "gemini";

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getZkNamespace() {
        return zkNamespace;
    }

    public void setZkNamespace(String zkNamespace) {
        this.zkNamespace = zkNamespace;
    }
}
