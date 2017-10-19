package com.sunchangpeng.gemini.scheduler.client;

import com.sunchangpeng.gemini.zookeeper.watcher.ChildListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sunchangpeng
 */
public class ZkJobRunner implements ChildListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZkJobRunner.class);

    /**
     * The job implements
     */
    private Job job;

    public ZkJobRunner(Job job) {
        this.job = job;
    }

    @Override
    public void onAdd(String path, String data) {
        LOGGER.info("onAdd {}", path);
        JobContext jobContext = new JobContext();
        job.execute(jobContext);
    }

    @Override
    public void onDelete(String path) {

    }

    @Override
    public void onUpdate(String path, String newData) {

    }
}
