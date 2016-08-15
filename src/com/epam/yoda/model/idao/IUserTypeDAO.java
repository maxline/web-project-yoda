package com.epam.yoda.model.idao;

import com.epam.yoda.model.entities.UserType;

import java.util.Map;

/**
 * @author Sergey Mikhluk.
 */
public interface IUserTypeDAO extends IDAO<UserType> {
    Map<Long, String> findAll();
}
