package com.sunchangpeng.gemini.scheduler.client.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by sunchangpeng
 */
@SpringBootApplication
@ComponentScan("com.sunchangpeng.gemini")
public class Bootstrap {
    public static void main(String[] args) {
        SpringApplication.run(Bootstrap.class, args);
    }
}
