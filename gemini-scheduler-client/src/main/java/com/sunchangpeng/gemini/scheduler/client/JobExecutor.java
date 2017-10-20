package com.sunchangpeng.gemini.scheduler.client;

import java.util.concurrent.ExecutorService;

/**
 * Created by sunchangpeng
 */
public class JobExecutor {
    private ExecutorService executor;

    public JobExecutor(ExecutorService executor) {
        this.executor = executor;
    }

    void execute(Long instanceId, Job job) {
        this.executor.submit(new ExecuteTask(instanceId, job));
    }

    private class ExecuteTask implements Runnable {
        private final Long instanceId;

        private final Job job;

        public ExecuteTask(Long instanceId, Job job) {
            this.instanceId = instanceId;
            this.job = job;
        }

        @Override
        public void run() {
            JobContext context = buildJobContext(instanceId, "");
            JobResult jobResult = this.job.execute(context);
        }

        private JobContext buildJobContext(Long instanceId, String jobParam) {
            JobContext context = new JobContext();

            context.setInstanceId(instanceId);
            context.setJobParam(jobParam);

            return context;
        }
    }
}
