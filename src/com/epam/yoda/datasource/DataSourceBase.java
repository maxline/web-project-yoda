package com.epam.yoda.datasource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Sergey Mikhluk.
 */
public abstract class DataSourceBase {

    public abstract Connection getConnection();

}
