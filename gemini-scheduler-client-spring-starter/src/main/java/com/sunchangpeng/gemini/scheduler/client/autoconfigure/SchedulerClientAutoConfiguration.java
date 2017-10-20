package com.sunchangpeng.gemini.scheduler.client.autoconfigure;

import com.sunchangpeng.gemini.scheduler.client.config.SchedulerClientConfig;
import com.sunchangpeng.gemini.scheduler.client.spring.SpringSchedulerClient;
import com.sunchangpeng.gemini.zookeeper.ZkTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by sunchangpeng
 */
@Configuration
@EnableConfigurationProperties(SchedulerClientProperties.class)
public class SchedulerClientAutoConfiguration {
    @Autowired
    private SchedulerClientProperties properties;

    @Bean
    public SchedulerClientConfig schedulerClientConfig() {
        SchedulerClientConfig bean = new SchedulerClientConfig();
        bean.setAppName(properties.getAppName());
        bean.setZkNamespace(properties.getZkNamespace());
        return bean;
    }

    @Bean
    public SpringSchedulerClient springSchedulerClient(SchedulerClientConfig clientConfig, ZkTemplate zkTemplate) throws Exception {
        SpringSchedulerClient bean = new SpringSchedulerClient(clientConfig, zkTemplate);
        return bean;
    }
}
