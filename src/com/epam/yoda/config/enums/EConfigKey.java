package com.epam.yoda.config.enums;

/**
 * @author Sergey Mikhluk.
 */
public enum EConfigKey {
    JDBC_RESOURCE_NAME("JDBC.RESOURCE_NAME"),
    PAGE_MAIN_USER("PAGE.MAIN_USER"),
    PAGE_MAIN_ADMIN("PAGE.MAIN_ADMIN"),
    PAGE_ERROR("PAGE.ERROR"),
    PAGE_LOGIN("PAGE.LOGIN");

    EConfigKey(String id) {
        this.id = id;
    }

    private final String id;

    public String getId() {
        return id;
    }
}
