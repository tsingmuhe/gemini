package com.sunchangpeng.gemini.zookeeper;

import com.sunchangpeng.gemini.zookeeper.utils.PathUtil;

/**
 * Created by sunchangpeng
 */
public class ZkPaths {
    public static final String JOBS = "/jobs";

    public static String pathOfJob(String appName, String jobClass) {
        return PathUtil.format(JOBS, appName, jobClass);
    }

    public static String pathOfJobState(String appName, String jobClass) {
        return PathUtil.format(JOBS, appName, jobClass, "state");
    }

    public static String pathOfJobScheduler(String appName, String jobClass) {
        return PathUtil.format(JOBS, appName, jobClass, "scheduler");
    }

    public static String pathOfJobInstances(String appName, String jobClass) {
        return PathUtil.format(JOBS, appName, jobClass, "instances");
    }

    public static String pathOfJobInstance(String appName, String jobClass, Long instanceId) {
        return PathUtil.format(JOBS, appName, jobClass, "instances", String.valueOf(instanceId));
    }
}
