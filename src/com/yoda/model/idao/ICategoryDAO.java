package com.yoda.model.idao;

import com.yoda.model.entities.Category;

import java.util.Map;

/**
 * @author Sergey Mikhluk.
 */
public interface ICategoryDAO extends IDAO<Category> {
    Map<Long, String> findAll();

    boolean add(Category category);

    boolean update(Category category);

    boolean delete(long id);
}
