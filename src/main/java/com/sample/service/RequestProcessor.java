package com.sample.service;

import com.sample.dao.mssql.repository.MSSqlRepository;
import com.sample.dao.postgres.entity.Request;
import com.sample.dao.postgres.entity.Status;
import com.sample.dao.postgres.repository.RequestRepository;
import com.sample.dto.InputRq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Map;

import static com.sample.dao.mssql.entity.ProcedureResult.*;

@Slf4j
@Component
public class RequestProcessor {
    @Autowired
    private RequestRepository rqRepository;

    @Autowired
    private MSSqlRepository msSqlRepository;

    public void receiveRq(InputRq rq) {
        Request entity = buildEntity(rq);
        entity = rqRepository.save(entity);

        boolean success = callStoredProcedure(rq);

        if (success) {
            entity.setStatus(Status.SENT);
        } else {
            entity.setStatus(Status.ERROR);
        }

        rqRepository.save(entity);
    }

    private Request buildEntity(InputRq rq) {
        Request result = new Request();

        result.setRqUid(rq.getRqUID());
        result.setCode(rq.getCode());
        result.setStart(rq.getStart());
        result.setEnd(rq.getEnd());
        result.setReceived(OffsetDateTime.now());
        result.setStatus(Status.RECEIVED);

        return result;
    }

    private boolean callStoredProcedure(InputRq rq) {
        String rqUID = rq.getRqUID();
        String code = rq.getCode();
        LocalDate start = rq.getStart();
        LocalDate end = rq.getEnd();

        log.info("start call ({}, {}, {}, {})...", rqUID, code, start, end);

        try {
            Map<String, Object> result = msSqlRepository.callStoredProcedure(
                    rqUID, code, start, end);

            log.info("result: {}", result);

            return MESSAGE_SUCCESS.equals(result.get(MESSAGE_FIELD));
        } catch (Exception e) {
            log.error(String.format("cannot call stored procedure '%s'", SP_SAMPLE), e);
            return false;
        }
    }
}