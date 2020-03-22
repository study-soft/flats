package com.ay.flats.repository;

import java.util.List;

public interface CommonRepository<T> {

    T save(T entity);

    List<T> findAll();
}
