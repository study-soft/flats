package com.ay.flats.repository;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import java.util.List;

public abstract class AbstractCommonRepository<T> implements CommonRepository<T> {

    private final MongoOperations operations;

    public AbstractCommonRepository(final MongoOperations operations) {
        this.operations = operations;
    }

    protected abstract Class<T> entityClass();

    @Override
    public T save(final T entity) {
        return operations.save(entity);
    }

    @Override
    public List<T> findAll() {
        return operations.findAll(entityClass());
    }

}