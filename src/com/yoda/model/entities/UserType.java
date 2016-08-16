package com.yoda.model.entities;

/**
 * @author Sergey Mikhluk.
 */
public class UserType {
    private long userTypeId = -1L;
    private String type;

    public UserType() {
    }

    public UserType(Long userTypeId, String type) {
        this.userTypeId = userTypeId;
        this.type = type;
    }

    public long getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(long userTypeId) {
        this.userTypeId = userTypeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}