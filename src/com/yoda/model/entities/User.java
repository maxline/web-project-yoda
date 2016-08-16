package com.yoda.model.entities;

/**
 * @author Sergey Mikhluk.
 */
public class User {
    private long userId = -1L;
    private String login;
    private String password;
    private int userTypeId;

    public User() {
    }

    public User(long userId, String login, String password, String userTypeId) {
        this.userId = userId;
        this.login = login;
        this.password = password;
        this.userTypeId = Integer.parseInt(userTypeId);
    }

    public User(long userId, String login, String password, int userTypeId) {
        this.userId = userId;
        this.login = login;
        this.password = password;
        this.userTypeId = userTypeId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(int userTypeId) {
        this.userTypeId = userTypeId;
    }
}