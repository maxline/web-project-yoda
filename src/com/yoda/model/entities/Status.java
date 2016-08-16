package com.yoda.model.entities;

/**
 * @author Sergey Mikhluk.
 */

public class Status {
    private long statusId = -1L;
    private String name;


    public Status() {
    }

    public Status(Long statusId, String name) {
        this.statusId = statusId;
        this.name = name;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long id) {
        this.statusId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}