package com.sunchangpeng.gemini.scheduler.job;

/**
 * Created by sunchangpeng
 */
public class JobInstanceWaitResp {

    /**
     * The job instance finished successfully or not
     */
    private boolean success;

    /**
     * The job instance is timeout or not
     */
    private boolean timeout;

    private JobInstanceWaitResp() {
    }

    public static JobInstanceWaitResp success() {
        JobInstanceWaitResp r = new JobInstanceWaitResp();
        r.success = true;
        return r;
    }

    public static JobInstanceWaitResp timeout() {
        JobInstanceWaitResp r = new JobInstanceWaitResp();
        r.timeout = true;
        return r;
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isTimeout() {
        return timeout;
    }
}
