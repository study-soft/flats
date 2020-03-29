package com.ay.flats.repository;

import com.ay.flats.domain.Flat;
import com.mongodb.bulk.BulkWriteResult;

import java.util.List;
import java.util.Optional;

public interface FlatRepository extends CommonRepository<Flat> {

    BulkWriteResult saveAll(List<Flat> flats);

    Optional<Flat> findFlatByOlxId(Long olxId);
}
