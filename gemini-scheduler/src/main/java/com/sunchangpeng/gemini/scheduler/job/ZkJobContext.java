package com.sunchangpeng.gemini.scheduler.job;

import com.sunchangpeng.gemini.common.ZkPaths;
import com.sunchangpeng.gemini.common.utils.CollectionUtil;
import com.sunchangpeng.gemini.zookeeper.ZkTemplate;
import com.sunchangpeng.gemini.zookeeper.watcher.NodeListener;
import com.sunchangpeng.gemini.zookeeper.watcher.NodeWatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


/**
 * Created by sunchangpeng
 */
@Component
public class ZkJobContext {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZkJobContext.class);

    @Autowired
    private ZkTemplate zkClient;


    public void addZkJobInstance(JobBean jobBean) {
        String appName = jobBean.getAppName();
        String jobClass = jobBean.getClazz();

        // create the job instances dir
        mkJobInstances(appName, jobClass);

        // create the job state node
        updateJobStateDirectly(appName, jobClass, JobState.WAITING);

        // create the job scheduler node
        updateJobScheduler(appName, jobClass, "127.0.0.1");
    }

    public void mkJobInstances(String appName, String jobClass) {
        String pathOfJobInstances = ZkPaths.pathOfJobInstances(appName, jobClass);

        zkClient.deleteRecursivelyIfExists(pathOfJobInstances);
        zkClient.ensurePath(pathOfJobInstances);
    }

    public void updateJobStateDirectly(String appName, String jobClass, JobState state) {
        String jobStateNode = ZkPaths.pathOfJobState(appName, jobClass);

        zkClient.ensurePath(jobStateNode);
        zkClient.update(jobStateNode, String.valueOf(state.code()));
    }

    public void updateJobScheduler(String appName, String jobClass, String scheduler) {
        String jobSchedulerNode = ZkPaths.pathOfJobScheduler(appName, jobClass);

        zkClient.ensurePath(jobSchedulerNode);
        zkClient.update(jobSchedulerNode, scheduler);
    }

    public boolean hasJobInstance(String appName, String jobClass) {
        String jobInstanceNodePath = ZkPaths.pathOfJobInstances(appName, jobClass);

        List<String> instances = zkClient.getChildren(jobInstanceNodePath);
        return !CollectionUtil.isNullOrEmpty(instances);
    }

    public void triggerJobInstance(String appName, String jobClass) {
        String jobInstancePath = ZkPaths.pathOfJobInstance(appName, jobClass, 1L);
        zkClient.create(jobInstancePath, "1");
    }

    public JobInstanceWaitResp waitingJobInstanceFinish(final String appName, final String jobClass, final Long jobInstanceId, long timeout) {
        final CountDownLatch latch = new CountDownLatch(1);

        String jobInstanceNode = ZkPaths.pathOfJobInstance(appName, jobClass, jobInstanceId);

        NodeWatcher watcher = zkClient.createNodeWatcher(jobInstanceNode, new NodeListener() {
            @Override
            public void onUpdate(String newData) {

            }

            @Override
            public void onDelete() {
                latch.countDown();
            }
        });

        try {
            LOGGER.info("Waiting the job({}/{}/{}) with timeout({}) to be finished.", appName, jobClass, jobInstanceId, timeout);

            if (timeout > 0L) {
                // need take in account the timeout
                if (!latch.await(timeout, TimeUnit.SECONDS)) {
                    return JobInstanceWaitResp.timeout();
                }
            } else {
                // no need take in account the timeout
                latch.await();
            }
        } catch (InterruptedException e) {
            // occur error
            throw new RuntimeException(e);
        } finally {
            if (watcher != null) {
                watcher.close();
            }
        }

        LOGGER.info("The job({}/{}/{}) has finished.", appName, jobClass, jobInstanceId);
        return JobInstanceWaitResp.success();
    }
}
