package com.ay.flats.repository;

import com.ay.flats.domain.Flat;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

@Repository
public class FlatRepository extends AbstractCommonRepository<Flat> implements CommonRepository<Flat> {

    public FlatRepository(final MongoOperations operations) {
        super(operations);
    }

    @Override
    protected Class<Flat> entityClass() {
        return Flat.class;
    }
}
