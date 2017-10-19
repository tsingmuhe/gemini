package com.sunchangpeng.gemini.scheduler.client.autoconfigure;

import com.sunchangpeng.gemini.common.utils.CollectionUtil;
import com.sunchangpeng.gemini.scheduler.client.Job;
import com.sunchangpeng.gemini.scheduler.client.SchedulerClient;
import com.sunchangpeng.gemini.scheduler.client.config.SchedulerClientConfig;
import com.sunchangpeng.gemini.zookeeper.ZkWatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * Created by sunchangpeng
 */
@Configuration
@EnableConfigurationProperties(SchedulerClientProperties.class)
public class SchedulerClientAutoConfiguration {
    @Autowired
    private ApplicationContext springContext;
    @Autowired
    private SchedulerClientProperties properties;
    @Autowired
    private ZkWatchers zkWatchers;

    @Bean
    public SchedulerClientConfig schedulerClientConfig() {
        SchedulerClientConfig bean = new SchedulerClientConfig();
        bean.setAppName(properties.getAppName());
        bean.setZkNamespace(properties.getZkNamespace());
        return bean;
    }

    @Bean
    public SchedulerClient schedulerClient() {
        SchedulerClient bean = new SchedulerClient(zkWatchers, schedulerClientConfig());
        registerJobs(bean);
        return bean;
    }

    private void registerJobs(SchedulerClient client) {
        // register default jobs
        Map<String, Job> jobs = springContext.getBeansOfType(Job.class);
        if (!CollectionUtil.isNullOrEmpty(jobs)) {
            for (Job job : jobs.values()) {
                client.registerJob(job);
            }
        }
    }
}
