package com.epam.yoda.datasource.factory;

import com.epam.yoda.config.enums.EDataSourceType;
import com.epam.yoda.datasource.DataSourceBase;
import com.epam.yoda.datasource.MySQLDataSource;

/**
 * @author Sergey Mikhluk.
 */
public class DataSourceFactory {

    public static DataSourceBase createConnection(EDataSourceType connectionTypes) throws ClassNotFoundException {
        switch (connectionTypes) {
            case MYSQL:
                return MySQLDataSource.getInstance();
            case ORACLE:
                throw new ClassNotFoundException("Unknown DB type " + connectionTypes);
            default:
                return MySQLDataSource.getInstance();
        }
    }
}
