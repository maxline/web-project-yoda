package com.yoda.model.idao;

import com.yoda.model.entities.Activity;

import java.util.Map;

/**
 * @author Sergey Mikhluk.
 */
public interface IActivityDAO extends IDAO<Activity> {
    Map<Long, String> findAll();

    boolean add(Activity activity);

    boolean update(Activity activity);

    boolean delete(long id);
}
