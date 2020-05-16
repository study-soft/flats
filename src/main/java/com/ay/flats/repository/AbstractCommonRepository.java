package com.ay.flats.repository;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.List;

public abstract class AbstractCommonRepository<T> implements CommonRepository<T> {

    protected final Logger LOG = getLogger();

    protected final MongoOperations localTemplate;
    protected final MongoOperations remoteTemplate;

    public AbstractCommonRepository(final @Qualifier("localMongoTemplate") MongoOperations localTemplate,
                                    final @Qualifier("remoteMongoTemplate") MongoOperations remoteTemplate) {
        this.localTemplate = localTemplate;
        this.remoteTemplate = remoteTemplate;
    }

    protected abstract Class<T> entityClass();

    protected abstract boolean needRemote();

    protected abstract Logger getLogger();

    @Override
    public T saveOrUpdate(final T entity) {
        T saved = localTemplate.save(entity);
        if (needRemote()) {
            remoteTemplate.save(entity);
            LOG.info("Saved to remote database: {}", entity);
        }
        return saved;
    }

    @Override
    public List<T> findAll() {
        List<T> all = localTemplate.findAll(entityClass());
        if (needRemote()) {
            remoteTemplate.findAll(entityClass());
        }
        return all;
    }

}
