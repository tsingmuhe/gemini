package com.sunchangpeng.gemini.scheduler.client;

/**
 * Created by sunchangpeng
 */
public interface Job {
    /**
     * Execute the job
     *
     * @param context the job context
     * @return the job result
     */
    JobResult execute(JobContext context);
}
