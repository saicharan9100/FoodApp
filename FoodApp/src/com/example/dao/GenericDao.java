package com.example.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T, ID extends Serializable> {
    void save(T entity);
    void update(T entity);
    void delete(T entity);
    T findById(Class<T> type, ID id);
    List<T> findAll(Class<T> type);
}
