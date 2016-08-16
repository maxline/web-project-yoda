package com.yoda.model.idao;

import com.yoda.model.entities.Status;

import java.util.Map;

/**
 * @author Sergey Mikhluk.
 */
public interface IStatusDAO extends IDAO<Status> {
    Map<Long, String> findAll();

    boolean add(Status status);

    boolean update(Status status);

    boolean delete(long id);
}
