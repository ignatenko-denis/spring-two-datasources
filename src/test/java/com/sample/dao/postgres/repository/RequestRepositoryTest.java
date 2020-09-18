package com.sample.dao.postgres.repository;

import com.sample.dao.postgres.entity.Request;
import com.sample.dao.postgres.entity.Status;
import com.sample.util.DateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

import static com.sample.dao.postgres.entity.Status.RECEIVED;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestPropertySource(locations = "/config/application.yml")
class RequestRepositoryTest {
    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    private RequestRepository rqRepository;

    @Test
    void findByRqUid() {
        Request rq = build();
        rqRepository.save(rq);

        Optional<Request> result = rqRepository.findById(rq.getId());

        assertTrue(result.isPresent());

        rq = result.get();
        validate(rq, RECEIVED);

        rqRepository.delete(rq);
    }

    private Request build() {
        Request result = new Request();

        result.setRqUid("2");
        result.setCode("XCODE");
        result.setStart(DateUtil.toDate("2020-01-01"));
        result.setEnd(DateUtil.toDate("2020-06-01"));
        result.setReceived(DateUtil.toDateTime("2020-01-01T22:01:34.000+0300"));
        result.setStatus(RECEIVED);

        return result;
    }

    private void validate(Request rq, Status status) {
        assertNotNull(rq.getId());
        assertEquals("2", rq.getRqUid());
        assertEquals("XCODE", rq.getCode());
        assertEquals("2020-01-01", DateUtil.format(rq.getStart()));
        assertEquals("2020-06-01", DateUtil.format(rq.getEnd()));
        assertEquals("2020-01-01T22:01:34.000+0300", DateUtil.format(rq.getReceived()));
        assertEquals(status, rq.getStatus());
    }
}