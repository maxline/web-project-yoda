package com.yoda.model.entities;

import org.apache.log4j.Logger;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author Sergey Mikhluk.
 */
public class Task {
    private static final Logger logger = Logger.getLogger(Task.class.getName());
    private long taskId = -1;
    private String name;
    private String content;
    private int categoryId;
    private int activityId;
    private int priority;
    private int statusId;
    private Date deadline;

    public Task(long taskId, String name, String content, int categoryId, int activityId, int priority, int statusId, Date deadline) {
        this.taskId = taskId;
        this.name = name;
        this.content = content;
        this.categoryId = categoryId;
        this.activityId = activityId;
        this.priority = priority;
        this.statusId = statusId;
        this.deadline = deadline;
    }

    public Task(long taskId, String name, String content, String categoryId, String activityId, String priority, String statusId, String deadline) {
        this.taskId = taskId;
        this.name = name;
        this.content = content;
        this.categoryId = Integer.parseInt(categoryId);
        this.activityId = Integer.parseInt(activityId);
        this.priority = Integer.parseInt(priority);
        this.statusId = Integer.parseInt(statusId);
        this.deadline = stringToDate(deadline);
    }

    private Date stringToDate(String deadline) {
        java.util.Date date_utl;
        try {
            date_utl = new SimpleDateFormat("yyyy-MM-dd").parse(deadline);
        } catch (ParseException e) {
            logger.info(e + ". Set date_utl = current date + 2 days.");
            Calendar today = Calendar.getInstance();
            today.setTime(new Date(Calendar.getInstance().getTimeInMillis()));
            today.add(Calendar.DATE, 2);
            date_utl = new Date(today.getTimeInMillis());
        }
        return new Date(date_utl.getTime());
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", categoryId=" + categoryId +
                ", activityId=" + activityId +
                ", priority=" + priority +
                ", status=" + statusId +
                '}';
    }
}
