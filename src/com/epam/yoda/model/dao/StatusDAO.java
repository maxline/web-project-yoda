package com.epam.yoda.model.dao;

import com.epam.yoda.model.entities.Status;
import com.epam.yoda.model.idao.IStatusDAO;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Sergey Mikhluk.
 */
public class StatusDAO extends DAOBase implements IStatusDAO {
    private static final Logger logger = Logger.getLogger(StatusDAO.class.getName());
    private static final int INITIAL_CAPACITY = 5;
    //language=MySQL
    private static final String SELECT_ALL_STATUSES = "SELECT * FROM status ORDER BY statusId";
    //language=MySQL
    private static final String SELECT_STATUS_BY_ID = "SELECT * FROM status WHERE statusId=?";
    //language=MySQL
    private static final String INSERT_STATUS = "INSERT INTO status(name) VALUES(?)";
    //language=MySQL
    private static final String UPDATE_STATUS = "UPDATE status SET name=? WHERE statusId=?";
    //language=MySQL
    private static final String DELETE_STATUS = "DELETE FROM status WHERE statusId=?";

    @Override
    public Map<Long, String> findAll() {
        Map<Long, String> map = new HashMap<>(INITIAL_CAPACITY);

        ResultSet rs = executeSelect(SELECT_ALL_STATUSES);
        try {
            while (rs.next()) {
                Status status = new Status(
                        rs.getLong("statusId"),
                        rs.getString("name")
                );
                map.put(status.getStatusId(), status.getName());
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            close(rs);
        }
        return map;
    }

    @Override
    public Status findById(long id) {
        ResultSet rs = executeSelect(SELECT_STATUS_BY_ID, Collections.singletonList(id));
        try {
            if (rs.next()) {
                return new Status(
                        rs.getLong("statusId"),
                        rs.getString("name")
                );
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            close(rs);
        }
        return null;
    }

    @Override
    public boolean add(Status status) {
        return executeUpdate(INSERT_STATUS,
                Collections.singletonList(status.getName()));
    }

    @Override
    public boolean update(Status status) {
        return executeUpdate(UPDATE_STATUS, Arrays.asList(
                status.getName(),
                status.getStatusId()
        ));
    }

    @Override
    public boolean delete(long id) {
        return executeUpdate(DELETE_STATUS,
                Collections.singletonList(id));
    }
}