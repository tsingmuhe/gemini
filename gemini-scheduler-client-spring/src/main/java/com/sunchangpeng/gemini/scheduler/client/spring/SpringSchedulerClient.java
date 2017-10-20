package com.sunchangpeng.gemini.scheduler.client.spring;

import com.sunchangpeng.gemini.common.utils.CollectionUtil;
import com.sunchangpeng.gemini.scheduler.client.Job;
import com.sunchangpeng.gemini.scheduler.client.SchedulerClient;
import com.sunchangpeng.gemini.scheduler.client.config.SchedulerClientConfig;
import com.sunchangpeng.gemini.zookeeper.ZkTemplate;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * Created by sunchangpeng
 */
public class SpringSchedulerClient extends SchedulerClient implements ApplicationContextAware {
    private ApplicationContext springContext;

    public SpringSchedulerClient(SchedulerClientConfig clientConfig, ZkTemplate zkTemplate) {
        super(clientConfig, zkTemplate);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.springContext = applicationContext;
    }

    @PostConstruct
    public void registerJobs() {
        // register default jobs
        Map<String, Job> jobs = springContext.getBeansOfType(Job.class);
        if (!CollectionUtil.isNullOrEmpty(jobs)) {
            for (Job job : jobs.values()) {
                registerJob(job);
            }
        }
    }
}
