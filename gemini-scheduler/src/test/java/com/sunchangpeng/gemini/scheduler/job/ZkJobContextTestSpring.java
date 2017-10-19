package com.sunchangpeng.gemini.scheduler.job;

import com.sunchangpeng.gemini.zookeeper.ZkClient;
import com.sunchangpeng.gemini.zookeeper.ZkPaths;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by sunchangpeng
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ZkJobContextTestSpring {
    @Autowired
    private ZkJobContext zkJobContext;
    @Autowired
    private ZkClient zkClient;

    @Test
    public void addZkJobInstance() throws InterruptedException {
        JobBean jobBean = new JobBean();

        jobBean.setJobId(1L);
        jobBean.setAppName("sunchangpeng");
        jobBean.setClazz("hello.world");
        jobBean.setCron("* * * * * ? *");
        jobBean.setMisfire(false);

        zkJobContext.addZkJobInstance(jobBean);
    }
}