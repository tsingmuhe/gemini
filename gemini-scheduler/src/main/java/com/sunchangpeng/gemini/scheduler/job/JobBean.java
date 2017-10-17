package com.sunchangpeng.gemini.scheduler.job;

import org.quartz.JobKey;
import org.quartz.TriggerKey;

/**
 * Created by sunchangpeng
 */
public class JobBean {
    private Long jobId;
    private String appName;
    private String clazz;
    private String cron;
    private Boolean misfire;

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public Boolean getMisfire() {
        return misfire;
    }

    public void setMisfire(Boolean misfire) {
        this.misfire = misfire;
    }


    public JobKey buildJobKey() {
        return JobKey.jobKey(clazz, appName);
    }

    public TriggerKey buildTriggerKey() {
        return TriggerKey.triggerKey(clazz, appName);
    }

    @Override
    public String toString() {
        return "JobBean{" +
                "jobId=" + jobId +
                ", appName='" + appName + '\'' +
                ", clazz='" + clazz + '\'' +
                ", cron='" + cron + '\'' +
                ", misfire=" + misfire +
                '}';
    }
}
