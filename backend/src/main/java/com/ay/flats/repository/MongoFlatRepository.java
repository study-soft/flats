package com.ay.flats.repository;

import com.ay.flats.domain.Flat;
import com.mongodb.bulk.BulkWriteResult;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

@Repository
public class MongoFlatRepository extends AbstractCommonRepository<Flat> implements CommonRepository<Flat>, FlatRepository {

    public MongoFlatRepository(final MongoOperations operations) {
        super(operations);
    }

    @Override
    protected Class<Flat> entityClass() {
        return Flat.class;
    }

    @Override
    public BulkWriteResult saveAll(List<Flat> flats) {
        BulkOperations bulkOperations = operations.bulkOps(BulkOperations.BulkMode.UNORDERED, Flat.class);
        flats.forEach(flat -> bulkOperations.upsert(
                query(where("olxId").is(flat.getOlxId())),
                update("name", flat.getName())
                        .set("url", flat.getUrl())
                        .set("priceUsd", flat.getPriceUsd())
                        .set("adDate", flat.getAdDate())
        ));
        return bulkOperations.execute();
    }
}
