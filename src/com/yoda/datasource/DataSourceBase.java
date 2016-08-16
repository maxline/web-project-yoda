package com.yoda.datasource;

import java.sql.Connection;

/**
 * @author Sergey Mikhluk.
 */
public abstract class DataSourceBase {

    public abstract Connection getConnection();

}
