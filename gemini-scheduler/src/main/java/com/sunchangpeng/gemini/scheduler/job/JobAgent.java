package com.sunchangpeng.gemini.scheduler.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Created by sunchangpeng
 */
public class JobAgent extends QuartzJobBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(JobAgent.class);

    @Autowired
    private ZkJobContext zkJobContext;

    private JobBean jobBean;

    public void setJobBean(JobBean jobBean) {
        this.jobBean = jobBean;
    }

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LOGGER.info("Job Agent execute {}", jobBean);

        final String appName = jobBean.getAppName();
        final String jobClass = jobBean.getClazz();

        if (!canRunJobInstance(appName, jobClass)) {
            return;
        }

        // job is running
        zkJobContext.updateJobStateDirectly(appName, jobClass, JobState.RUNNING);

        // trigger the clients
        zkJobContext.triggerJobInstance(appName, jobClass);
        LOGGER.info("Trigger Job Instance {}", jobBean);

//        JobInstanceWaitResp finishResp = zkJobContext.waitingJobInstanceFinish(appName, jobClass, 1L, 10);
//
//        if (finishResp.isSuccess()) {
//            LOGGER.info("Job Instance Finish  {}", jobBean);
//        } else if (finishResp.isTimeout()) {
//            LOGGER.error("Job Instance timeout {}", jobBean);
//        }

        // maybe now the job is paused, stopped, ..., so need to expect the job state
        zkJobContext.updateJobStateDirectly(appName, jobClass, JobState.WAITING);
    }

    private boolean canRunJobInstance(String appName, String jobClass) {
        if (zkJobContext.hasJobInstance(appName, jobClass)) {
            LOGGER.warn("The job({}/{}) has a running instance, so ignore this execution.", appName, jobClass);
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }
}
