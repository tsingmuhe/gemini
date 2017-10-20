package com.sunchangpeng.gemini.scheduler.client;

/**
 * Created by sunchangpeng
 */
public class JobContext {
    private Long instanceId;

    private String jobParam;

    public Long getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Long instanceId) {
        this.instanceId = instanceId;
    }

    public String getJobParam() {
        return jobParam;
    }

    public void setJobParam(String jobParam) {
        this.jobParam = jobParam;
    }
}
