package com.sunchangpeng.gemini.scheduler.client.config;

/**
 * Created by sunchangpeng
 */
public class SchedulerClientConfig {
    /**
     * The app name
     */
    private String appName = "sunchp";

    /**
     * The zk namespace
     */
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
