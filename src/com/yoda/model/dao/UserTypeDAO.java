package com.yoda.model.dao;

import com.yoda.model.entities.UserType;
import com.yoda.model.idao.IUserTypeDAO;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Sergey Mikhluk.
 */
public class UserTypeDAO extends DAOBase implements IUserTypeDAO {
    private static final Logger logger = Logger.getLogger(UserTypeDAO.class.getName());
    private static final int INITIAL_CAPACITY = 5;
    private static final String SELECT_ALL_USER_TYPES = "SELECT * FROM usertype";
    private static final String SELECT_USER_TYPE_BY_ID = "SELECT * FROM usertype WHERE usertypeId=?";

    @Override
    public Map<Long, String> findAll() {
        Map<Long, String> map = new HashMap<>(INITIAL_CAPACITY);
        ResultSet rs = executeSelect(SELECT_ALL_USER_TYPES);

        try {
            while (rs.next()) {
                UserType userType = new UserType(
                        rs.getLong("userTypeId"),
                        rs.getString("type")
                );
                map.put(userType.getUserTypeId(), userType.getType());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs);
        }
        return map;
    }

    @Override
    public UserType findById(long id) {
        ResultSet rs = executeSelect(SELECT_USER_TYPE_BY_ID, Collections.singletonList(id));
        try {
            if (rs.next()) {
                return new UserType(
                        rs.getLong("userTypeId"),
                        rs.getString("type")
                );
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            close(rs);
        }
        return null;
    }
}