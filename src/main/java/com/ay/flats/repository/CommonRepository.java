package com.ay.flats.repository;

import java.util.List;

public interface CommonRepository<T> {

    T saveOrUpdate(T entity);

    List<T> findAll();
}
