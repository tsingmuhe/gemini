package com.sunchangpeng.gemini.scheduler.job;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by sunchangpeng
 */
@Component
public class JobPool {
    private static final Logger LOGGER = LoggerFactory.getLogger(JobPool.class);

    @Autowired
    private Scheduler scheduler;
    @Autowired
    private ZkJobContext zkJobContext;

    public void scheduleJob(JobBean jobBean) throws SchedulerException {
        JobDetail quartzJob = newJob(JobAgent.class)
                .withIdentity(jobBean.buildJobKey())
                .usingJobData(buildJobData(jobBean))
                .build();

        CronScheduleBuilder scheduleBuilder = cronSchedule(jobBean.getCron());
        if (jobBean.getMisfire()) {
            // ignore all misfired
            //所有的misfire不管，执行下一个周期的任务
            scheduleBuilder.withMisfireHandlingInstructionDoNothing();
        } else {
            //合并部分的misfire,正常执行下一个周期的任务
            scheduleBuilder.withMisfireHandlingInstructionFireAndProceed();
        }

        Trigger trigger = newTrigger()
                .withIdentity(jobBean.buildTriggerKey())
                .withSchedule(scheduleBuilder)
                .build();

        this.scheduler.scheduleJob(quartzJob, trigger);
        LOGGER.info("schedule job {}", jobBean);

        zkJobContext.addZkJobInstance(jobBean);
        LOGGER.info("add Zk Job Instance {}", jobBean);
    }

    private JobDataMap buildJobData(JobBean jobBean) {
        JobDataMap jobData = new JobDataMap();
        jobData.put("jobBean", jobBean);
        return jobData;
    }
}
