package com.sunchangpeng.gemini.scheduler.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Created by sunchangpeng
 */
public class JobAgent extends QuartzJobBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(JobAgent.class);

    private JobBean jobBean;

    public void setJobBean(JobBean jobBean) {
        this.jobBean = jobBean;
    }

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LOGGER.info("Job Agent execute {}", jobBean);
    }
}
