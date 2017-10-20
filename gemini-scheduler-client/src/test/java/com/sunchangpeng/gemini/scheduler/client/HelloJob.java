package com.sunchangpeng.gemini.scheduler.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sunchangpeng
 */
public class HelloJob implements Job {
    private static final Logger LOGGER = LoggerFactory.getLogger(HelloJob.class);

    @Override
    public JobResult execute(JobContext context) {
        LOGGER.info("hello sunchangpeng");
        System.out.println("hello sunchangpeng");
        return JobResult.SUCCESS;
    }
}
