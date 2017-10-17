package com.sunchangpeng.gemini.scheduler.configuration.quartz;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunchangpeng
 */
@ConfigurationProperties("spring.quartz")
public class QuartzProperties {
    private final Map<String, String> properties = new HashMap<>();

    public Map<String, String> getProperties() {
        return this.properties;
    }
}
