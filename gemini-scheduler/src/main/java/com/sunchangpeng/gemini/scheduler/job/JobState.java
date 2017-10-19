package com.sunchangpeng.gemini.scheduler.job;

import java.util.Objects;

/**
 * Created by sunchangpeng
 */
public enum JobState {
    DISABLE(0, "job.state.disable"),
    WAITING(1, "job.state.waiting"),
    RUNNING(2, "job.state.running"),
    STOPPED(3, "job.state.stopped"),
    FAILED(4, "job.state.failed"),
    PAUSED(5, "job.state.paused");

    private Integer code;
    private String value;

    JobState(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer code() {
        return code;
    }

    public String value() {
        return value;
    }

    public static JobState from(Integer state) {
        for (JobState s : JobState.values()) {
            if (Objects.equals(s.code, state)) {
                return s;
            }
        }

        throw new IllegalStateException("invalid job state value: " + state);
    }

    public static Boolean isScheduling(JobState state) {
        return state == WAITING
                || state == RUNNING
                || state == PAUSED
                || state == FAILED;
    }
}
