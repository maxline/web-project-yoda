package com.epam.yoda.model.dao;

import com.epam.yoda.model.entities.Activity;
import com.epam.yoda.model.idao.IActivityDAO;
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
public class ActivityDAO extends DAOBase implements IActivityDAO {
    private static final Logger logger = Logger.getLogger(ActivityDAO.class.getName());
    private static final int INITIAL_CAPACITY = 16;
    //language=MySQL
    private static final String SELECT_ALL_ACTIVITIES = "SELECT * FROM activity ORDER BY activityId";
    //language=MySQL
    private static final String SELECT_ACTIVITY_BY_ID = "SELECT * FROM activity WHERE activityId=?";
    //language=MySQL
    private static final String INSERT_ACTIVITY = "INSERT INTO activity(name) VALUES(?)";
    //language=MySQL
    private static final String UPDATE_ACTIVITY = "UPDATE activity SET name=? WHERE activityId=?";
    //language=MySQL
    private static final String DELETE_ACTIVITY = "DELETE FROM activity WHERE activityId=?";

    @Override
    public Map<Long, String> findAll() {
        Map<Long, String> map = new HashMap<>(INITIAL_CAPACITY);
        ResultSet rs = executeSelect(SELECT_ALL_ACTIVITIES);

        try {
            while (rs.next()) {
                Activity activity = new Activity(
                        rs.getLong("activityId"),
                        rs.getString("name")
                );
                map.put(activity.getActivityId(), activity.getName());
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            close(rs);
        }
        return map;
    }

    @Override
    public Activity findById(long id) {
        ResultSet rs = executeSelect(SELECT_ACTIVITY_BY_ID, Collections.singletonList(id));
        try {
            if (rs.next()) {
                return new Activity(
                        rs.getLong("activityId"),
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
    public boolean add(Activity activity) {
        return executeUpdate(INSERT_ACTIVITY,
                Collections.singletonList(activity.getName()));
    }

    @Override
    public boolean update(Activity activity) {
        return executeUpdate(UPDATE_ACTIVITY, Arrays.asList(
                activity.getName(),
                activity.getActivityId()
        ));
    }

    @Override
    public boolean delete(long id) {
        return executeUpdate(DELETE_ACTIVITY,
                Collections.singletonList(id));
    }
}
