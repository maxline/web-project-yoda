package com.yoda.model.entities;

/**
 * @author Sergey Mikhluk.
 */
public class Activity {
    private long activityId = -1L;
    private String name;


    public Activity() {
    }

    public Activity(Long activityId, String name) {
        this.activityId = activityId;
        this.name = name;
    }

    public long getActivityId() {
        return activityId;
    }

    public void setActivityId(long activityId) {
        this.activityId = activityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}