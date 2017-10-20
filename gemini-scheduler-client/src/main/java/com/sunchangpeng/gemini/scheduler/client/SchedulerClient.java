package com.sunchangpeng.gemini.scheduler.client;

import com.google.common.collect.Maps;
import com.sunchangpeng.gemini.scheduler.client.config.SchedulerClientConfig;
import com.sunchangpeng.gemini.zookeeper.ZkPaths;
import com.sunchangpeng.gemini.zookeeper.ZkTemplate;
import com.sunchangpeng.gemini.zookeeper.watcher.ChildWatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * Created by sunchangpeng
 */
public class SchedulerClient implements Closeable {
    private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerClient.class);

    private SchedulerClientConfig clientConfig;
    private ZkTemplate zkTemplate;
    private JobExecutor jobExecutor;
    private Map<String, ChildWatcher> jobs = Maps.newConcurrentMap();

    public SchedulerClient(SchedulerClientConfig clientConfig, ZkTemplate zkTemplate, JobExecutor jobExecutor) {
        this.clientConfig = clientConfig;
        this.zkTemplate = zkTemplate;
        this.jobExecutor = jobExecutor;
    }

    public JobExecutor getJobExecutor() {
        return jobExecutor;
    }

    /**
     * Register the job
     *
     * @param job the job
     */
    public void registerJob(Job job) {
        final String jobClass = job.getClass().getName();

        if (jobs.containsKey(jobClass)) {
            return;
        }

        ZkJobRunner jobRunner = new ZkJobRunner(jobExecutor, job);
        ChildWatcher watcher = createWatcher(clientConfig.getAppName(), jobClass, jobRunner);
        jobs.put(jobClass, watcher);

        LOGGER.info("registered the job: {}", job);
    }

    private ChildWatcher createWatcher(String appName, String jobClass, ZkJobRunner jobRunner) {
        String jobInstancesNodePath = ZkPaths.pathOfJobInstances(appName, jobClass);
        return zkTemplate.createChildWatcher(jobInstancesNodePath, Boolean.TRUE, jobRunner);
    }

    @Override
    public void close() throws IOException {
        if (this.jobs == null) {
            return;
        }

        Collection<ChildWatcher> watchers = this.jobs.values();
        for (ChildWatcher watcher : watchers) {
            watcher.close();
        }

        this.jobs = null;
    }
}
