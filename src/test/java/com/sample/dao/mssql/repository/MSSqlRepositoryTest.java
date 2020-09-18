package com.sample.dao.mssql.repository;

import com.sample.util.DateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

import static com.sample.dao.mssql.entity.ProcedureResult.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "/config/application.yml")
class MSSqlRepositoryTest {
    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    private MSSqlRepository msSqlRepository;

    @Test
    void callBrokerReportMain() {
        String rqUID = UUID.randomUUID().toString();
        String code = "XCODE";
        LocalDate start = DateUtil.toDate("2020-01-01");
        LocalDate end = DateUtil.toDate("2020-06-01");

        Map<String, Object> result = null;
        try {
            result = msSqlRepository.callStoredProcedure(rqUID, code, start, end);
        } catch (Exception e) {
            fail(e);
        }

        assertEquals(MESSAGE_SUCCESS, result.get(MESSAGE_FIELD));
        assertNotNull(result.get(PROCESS_ID_FIELD));
    }
}