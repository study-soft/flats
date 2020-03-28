package com.ay.flats

import com.ay.flats.domain.Flat
import com.ay.flats.repository.FlatRepository
import com.mongodb.bulk.BulkWriteResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import java.time.LocalDateTime

@SpringBootTest(classes = Service)
class FlatRepositoryTest extends Specification {

    @Autowired
    private FlatRepository repository

    def "should save all new flats"() {
        given:
        List<Flat> givenFlats = []
        LocalDateTime now = LocalDateTime.now()
        and:
        1.upto(3, { i ->
            givenFlats.add(new Flat(
                    id: i,
                    olxId: i,
                    name: 'flat_' + i,
                    url: 'url_' + i,
                    priceUsd: i * 1000,
                    adDate: now.plusDays(i as long)
            ))
        })
        and:
        repository.save(new Flat(
                id: givenFlats[0].id,
                olxId: 1,
                name: 'flat_1',
                url: 'url_1',
                priceUsd: 1000,
                adDate: now.plusDays(1)
        ))

        when:
        BulkWriteResult result = repository.saveAll(givenFlats)

        then:
        result.getUpserts().size() == 2
    }
}
