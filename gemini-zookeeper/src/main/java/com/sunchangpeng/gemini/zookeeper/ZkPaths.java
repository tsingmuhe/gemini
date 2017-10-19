package com.sunchangpeng.gemini.zookeeper;

import static com.sunchangpeng.gemini.zookeeper.utils.ZkPathUtil.format;

/**
 * Created by sunchangpeng
 */
public final class ZkPaths {
    public static final String JOBS = "/jobs";

    public static String pathOfJob(String appName, String jobClass) {
        return format(JOBS, appName, jobClass);
    }

    public static String pathOfJobState(String appName, String jobClass) {
        return format(JOBS, appName, jobClass, "state");
    }

    public static String pathOfJobScheduler(String appName, String jobClass) {
        return format(JOBS, appName, jobClass, "scheduler");
    }

    public static String pathOfJobInstances(String appName, String jobClass) {
        return format(JOBS, appName, jobClass, "instances");
    }

    public static String pathOfJobInstance(String appName, String jobClass, Long instanceId) {
        return format(JOBS, appName, jobClass, "instances", String.valueOf(instanceId));
    }
}
