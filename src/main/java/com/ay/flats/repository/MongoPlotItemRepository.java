package com.ay.flats.repository;

import com.ay.flats.domain.PlotItem;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

@Repository
public class MongoPlotItemRepository extends AbstractCommonRepository<PlotItem> implements CommonRepository<PlotItem> {

    public MongoPlotItemRepository(final MongoOperations operations) {
        super(operations);
    }

    @Override
    protected Class<PlotItem> entityClass() {
        return PlotItem.class;
    }
}
