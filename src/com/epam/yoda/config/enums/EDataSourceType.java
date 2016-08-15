package com.epam.yoda.config.enums;

/**
 * @author Sergey Mikhluk.
 */
public enum EDataSourceType {
    MYSQL("MYSQL"),
    ORACLE("ORACLE");

    EDataSourceType(String id) {
        this.id = id;
    }

    private final String id;

    public String getId() {
        return id;
    }
}
