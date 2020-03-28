package com.ay.flats.repository;

import com.ay.flats.domain.Flat;
import com.mongodb.bulk.BulkWriteResult;

import java.util.List;

public interface FlatRepository extends CommonRepository<Flat> {

    BulkWriteResult saveAll(List<Flat> flats);
}
