package com.yoda.model.dao;

import com.yoda.config.enums.EDataSourceType;
import com.yoda.datasource.factory.DataSourceFactory;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Sergey Mikhluk.
 */
abstract class DAOBase {
    private static final Logger logger = Logger.getLogger(DAOBase.class.getName());
    private PreparedStatement stmtSelect;
    private Connection connSelect;

    private Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            connection = DataSourceFactory.createConnection(EDataSourceType.MYSQL).getConnection();
        } catch (ClassNotFoundException e) {
            logger.error(e);
        }
        return connection;
    }

    protected ResultSet executeSelect(String sql) {
        ResultSet rs = null;
        try {
            connSelect = getConnection();
            stmtSelect = connSelect.prepareStatement(sql);
            logger.debug("executeSelect() stmtSelect: " + stmtSelect);
            rs = stmtSelect.executeQuery();
        } catch (SQLException e) {
            logger.error(e);
        }
        return rs;
    }

    ResultSet executeSelect(String sql, List paramList) {
        ResultSet rs = null;
        try {
            connSelect = getConnection();
            stmtSelect = connSelect.prepareStatement(sql);
            for (int index = 0; index < paramList.size(); index++) {
                stmtSelect.setObject(index + 1, paramList.get(index));
            }
            logger.debug("executeSelect() stmtSelect: " + stmtSelect);
            rs = stmtSelect.executeQuery();
        } catch (SQLException e) {
            logger.error(e);
        }
        return rs;
    }

    boolean executeUpdate(String sql, List paramList) {
        boolean executeStatus = false;
        Connection connUpdate = null;
        PreparedStatement stmtUpdate = null;

        try {
            connUpdate = getConnection();
            stmtUpdate = connUpdate.prepareStatement(sql);

            for (int index = 0; index < paramList.size(); index++) {
                stmtUpdate.setObject(index + 1, paramList.get(index));
            }

            connUpdate.setAutoCommit(false);
            executeStatus = stmtUpdate.executeUpdate() > 0;
            connUpdate.commit();

        } catch (SQLException e) {
//            try {
//                connUpdate.rollback();
//            } catch (SQLException e1) {
//                e1.printStackTrace();
//            }
            logger.error("executeUpdate() " + e);
        } finally {
            try {
                if (stmtUpdate != null) {
                    stmtUpdate.close();  //todo затираем предыдущие ексепшены
                }
                if (connUpdate != null) {
                    connUpdate.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return executeStatus;
    }

    void close(ResultSet rs) {
        try {
            rs.close();
            stmtSelect.close();
            connSelect.close();
        } catch (Exception e) {
            logger.error("close() " + e);
        }
    }
}
