package com.sunchangpeng.gemini.scheduler.job;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by sunchangpeng
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JobPoolTestSpring {
    @Autowired
    private JobPool jobPool;
    @Autowired
    private ZkJobContext zkJobContext;

    @Test
    public void schedule_job() throws SchedulerException, InterruptedException {
        JobBean jobBean = new JobBean();

        jobBean.setJobId(1L);
        jobBean.setAppName("sunchangpeng");
        jobBean.setClazz("com.sunchangpeng.gemini.scheduler.client.sample.HelloJob");
        jobBean.setCron("0 0/1 * * * ? ");
        jobBean.setMisfire(false);

        jobPool.scheduleJob(jobBean);

        Thread.sleep(Integer.MAX_VALUE);
    }

}