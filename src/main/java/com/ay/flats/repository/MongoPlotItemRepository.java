package com.ay.flats.repository;

import com.ay.flats.domain.PlotItem;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

@Repository
public class MongoPlotItemRepository extends AbstractCommonRepository<PlotItem> implements CommonRepository<PlotItem> {

    public MongoPlotItemRepository(@Qualifier("localMongoTemplate") final MongoOperations localTemplate,
                                   @Qualifier("remoteMongoTemplate") final MongoOperations remoteTemplate) {
        super(localTemplate, remoteTemplate);
    }

    @Override
    protected Class<PlotItem> entityClass() {
        return PlotItem.class;
    }

    @Override
    protected boolean needRemote() {
        return true;
    }
}
