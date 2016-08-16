package com.yoda.model.idao;

import com.yoda.model.entities.UserType;

import java.util.Map;

/**
 * @author Sergey Mikhluk.
 */
public interface IUserTypeDAO extends IDAO<UserType> {
    Map<Long, String> findAll();
}
