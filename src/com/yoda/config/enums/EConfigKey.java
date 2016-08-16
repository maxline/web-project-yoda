package com.yoda.config.enums;

/**
 * @author Sergey Mikhluk.
 */
public enum EConfigKey {
    JDBC_RESOURCE_NAME("JDBC.RESOURCE_NAME"),
    PAGE_MAIN_USER("PAGE.MAIN_USER"),
    PAGE_MAIN_ADMIN("PAGE.MAIN_ADMIN"),
    PAGE_LOGIN("PAGE.LOGIN"),
    COOKIE_EXPIRY_SECONDS("COOKIE.EXPIRY_SECONDS");

    EConfigKey(String id) {
        this.id = id;
    }

    private final String id;

    public String getId() {
        return id;
    }
}
