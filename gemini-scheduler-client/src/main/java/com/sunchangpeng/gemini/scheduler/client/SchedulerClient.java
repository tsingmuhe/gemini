package com.sunchangpeng.gemini.scheduler.client;

import com.google.common.collect.Maps;
import com.sunchangpeng.gemini.scheduler.client.config.SchedulerClientConfig;
import com.sunchangpeng.gemini.zookeeper.ZkPaths;
import com.sunchangpeng.gemini.zookeeper.ZkWatchers;
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

    private ZkWatchers zkWatchers;
    private SchedulerClientConfig clientConfig;
    private Map<String, ChildWatcher> jobs = Maps.newConcurrentMap();

    public SchedulerClient(ZkWatchers zkWatchers, SchedulerClientConfig clientConfig) {
        this.zkWatchers = zkWatchers;
        this.clientConfig = clientConfig;
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

        ZkJobRunner jobRunner = new ZkJobRunner(job);
        ChildWatcher watcher = createWatcher(clientConfig.getAppName(), jobClass, jobRunner);
        jobs.put(jobClass, watcher);

        LOGGER.info("registered the job: {}", job);
    }

    private ChildWatcher createWatcher(String appName, String jobClass, ZkJobRunner jobRunner) {
        String jobInstancesNodePath = ZkPaths.pathOfJobInstances(appName, jobClass);
        return zkWatchers.createChildWatcher(jobInstancesNodePath, Boolean.TRUE, jobRunner);
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
