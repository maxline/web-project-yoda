package com.yoda.model.idao;

import com.yoda.model.entities.User;

import java.util.List;

/**
 * @author Sergey Mikhluk.
 */
public interface IUserDAO extends IDAO<User> {

    List<User> findAll();

    User find(String login, String password);

    User findByLogin(String login);

    boolean add(User element);

    boolean update(User element);

    boolean delete(long id);
}
