package com.epam.yoda.model.dao;

import com.epam.yoda.model.entities.User;
import com.epam.yoda.model.idao.IUserDAO;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Sergey Mikhluk.
 */
public class UserDAO extends DAOBase implements IUserDAO {
    private static final Logger logger = Logger.getLogger(UserDAO.class.getName());
    private static final int INITIAL_CAPACITY = 8;
    //language=MySQL
    private static final String SELECT_ALL_USERS = "SELECT * FROM user ORDER BY userId";
    //language=MySQL
    private static final String SELECT_USER_BY_ID = "SELECT * FROM user WHERE userId=?";
    //language=MySQL
    private static final String SELECT_USER_BY_LOGIN = "SELECT * FROM user WHERE login=?";
    //language=MySQL
    private static final String SELECT_USER_BY_LOGIN_ADN_PASSWORD = "SELECT * FROM user WHERE login=? AND password=?";
    //language=MySQL
    private static final String INSERT_USER = "INSERT INTO user(login, password, usertypeId) VALUES(?,?,?)";
    //language=MySQL
    private static final String UPDATE_USER = "UPDATE user SET login=?, password=?, usertypeId=? WHERE userId=?";
    //language=MySQL
    private static final String DELETE_USER = "DELETE FROM user WHERE userId=?";

    @Override
    public User find(String login, String password) {
        ResultSet rs = executeSelect(SELECT_USER_BY_LOGIN_ADN_PASSWORD, Arrays.asList(login, password));
        return getUserFromQuery(rs);
    }

    @Override
    public User findByLogin(String login) {
        ResultSet rs = executeSelect(SELECT_USER_BY_LOGIN, Collections.singletonList(login));
        return getUserFromQuery(rs);
    }

    @Override
    public User findById(long id) {
        ResultSet rs = executeSelect(SELECT_USER_BY_ID, Collections.singletonList(id));
        return getUserFromQuery(rs);
    }

    private User getUserFromQuery(ResultSet rs) {
        try {
            if (rs.next()) {
                return new User(
                        rs.getLong("userId"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getInt("userTypeId")
                );
            }
            return null;
        } catch (SQLException e) {
            logger.error(e);
            return null;
        } finally {
            close(rs);
        }
    }

    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<>(INITIAL_CAPACITY);
        ResultSet rs = executeSelect(SELECT_ALL_USERS);

        try {
            while (rs.next()) {
                User user = new User(
                        rs.getLong("userId"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getInt("userTypeId")
                );
                list.add(user);
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            close(rs);
        }
        return list;
    }

    @Override
    public boolean add(User user) {
        return executeUpdate(INSERT_USER, Arrays.asList(
                user.getLogin(),
                user.getPassword(),
                user.getUserTypeId()
        ));
    }

    @Override
    public boolean update(User user) {
        return executeUpdate(UPDATE_USER, Arrays.asList(
                user.getLogin(),
                user.getPassword(),
                user.getUserTypeId(),
                user.getUserId()
        ));
    }

    @Override
    public boolean delete(long id) {
        return executeUpdate(DELETE_USER,
                Collections.singletonList(id));
    }
}