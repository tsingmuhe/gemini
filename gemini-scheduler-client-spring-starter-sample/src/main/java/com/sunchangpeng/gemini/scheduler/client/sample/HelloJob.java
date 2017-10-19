package com.sunchangpeng.gemini.scheduler.client.sample;

import com.sunchangpeng.gemini.scheduler.client.Job;
import com.sunchangpeng.gemini.scheduler.client.JobContext;
import com.sunchangpeng.gemini.scheduler.client.JobResult;
import com.sunchangpeng.gemini.zookeeper.ZkClient;
import com.sunchangpeng.gemini.zookeeper.ZkPaths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by sunchangpeng
 */
@Component
public class HelloJob implements Job {
    @Autowired
    private ZkClient zkClient;

    @Override
    public JobResult execute(JobContext context) {
        System.out.println("hello sunchangpeng");
        String jobInstancesNodePath = ZkPaths.pathOfJobInstance("sunchangpeng", "com.sunchangpeng.gemini.scheduler.client.sample.HelloJob", 1L);
        zkClient.deleteRecursivelyIfExists(jobInstancesNodePath);
        return JobResult.SUCCESS;
    }
}
