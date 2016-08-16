package com.yoda.model.idao;

/**
 * @author Sergey Mikhluk.
 */
public interface IDAO<T> {
    T findById(long id);
}
