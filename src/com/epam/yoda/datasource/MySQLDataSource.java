package com.epam.yoda.datasource;

import com.epam.yoda.config.manager.ConfigManager;
import org.apache.log4j.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static com.epam.yoda.config.enums.EConfigKey.JDBC_RESOURCE_NAME;

/**
 * @author Sergey Mikhluk.
 */
public class MySQLDataSource extends DataSourceBase {
    private static final Logger logger = Logger.getLogger(MySQLDataSource.class.getName());
    private static volatile MySQLDataSource instance;
    private DataSource dataSource;

    private MySQLDataSource() {
    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            logger.error(e);
        }
        return null;
    }

    private void initializeDataSource() {
        ConfigManager configManager = ConfigManager.getInstance();
        try {
            InitialContext initialContext = new InitialContext();
            dataSource = (DataSource) initialContext.lookup(configManager.getProperty(JDBC_RESOURCE_NAME));

        } catch (NamingException e) {
            logger.error(e);
        }
    }

    public static MySQLDataSource getInstance() {
        MySQLDataSource localInstance = instance;
        if (localInstance == null) {
            synchronized (MySQLDataSource.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new MySQLDataSource();
                    instance.initializeDataSource();
                }
            }
        }
        return localInstance;
    }
}
