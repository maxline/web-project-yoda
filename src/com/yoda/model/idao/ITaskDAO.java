package com.yoda.model.idao;

import com.yoda.model.daohelper.QueryFilter;
import com.yoda.model.entities.Task;

import java.util.List;

/**
 * @author Sergey Mikhluk.
 */
public interface ITaskDAO extends IDAO<Task> {

    List<Task> findAll();

    List<Task> findByFilter(QueryFilter queryFilter);

    boolean add(Task element);

    boolean update(Task element);

    boolean delete(long id);
}
