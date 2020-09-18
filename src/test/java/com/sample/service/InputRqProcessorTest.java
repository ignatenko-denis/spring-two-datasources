package com.sample.service;

import com.sample.dao.mssql.repository.MSSqlRepository;
import com.sample.dao.postgres.entity.Request;
import com.sample.dao.postgres.entity.Status;
import com.sample.dao.postgres.repository.RequestRepository;
import com.sample.dto.InputRq;
import com.sample.util.DateUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.jdbc.IncorrectResultSetColumnCountException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.sample.dao.mssql.entity.ProcedureResult.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class InputRqProcessorTest {
    @Mock
    private RequestRepository rqRepository;

    @Mock
    private MSSqlRepository msSqlRepository;

    @InjectMocks
    private RequestProcessor processor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        when(rqRepository.save(any())).then(new Answer<Request>() {
            @Override
            public Request answer(InvocationOnMock invocation) {
                Request result = invocation.getArgument(0);

                result.setId(1L);

                return result;
            }
        });
    }

    @Test
    void receiveRqOK() {
        prepareMsSql(false, false);

        runCommon();

        verify(rqRepository, times(2)).save(argThat(obj -> obj.getStatus() == Status.SENT));
        verify(msSqlRepository, times(1)).callStoredProcedure(any(), any(), any(), any());
        verifyNoMoreInteractions(rqRepository);
    }

    @Test
    void receiveRqError() {
        prepareMsSql(true, false);

        runCommon();

        verify(rqRepository, times(2)).save(argThat(obj -> obj.getStatus() == Status.ERROR));
    }

    @Test
    void receiveRqException() {
        prepareMsSql(false, true);

        runCommon();

        verify(rqRepository, times(2)).save(argThat(obj -> obj.getStatus() == Status.ERROR));
    }

    private void runCommon() {
        String key = UUID.randomUUID().toString();

        InputRq rq = new InputRq();

        rq.setRqUID("1");
        rq.setCode("XCODE");
        rq.setStart(DateUtil.toDate("2020-01-01"));
        rq.setEnd(DateUtil.toDate("2020-06-01"));

        processor.receiveRq(rq);
    }

    private void prepareMsSql(boolean error, boolean exception) {
        Map<String, Object> map = new HashMap<>();

        map.put(MESSAGE_FIELD, error ? "ERROR" : MESSAGE_SUCCESS);
        map.put(PROCESS_ID_FIELD, "1");

        when(msSqlRepository.callStoredProcedure(any(), any(), any(), any())).thenReturn(map);

        if (exception) {
            IncorrectResultSetColumnCountException e = new IncorrectResultSetColumnCountException("error", 2, 1);
            when(msSqlRepository.callStoredProcedure(any(), any(), any(), any())).thenThrow(e);
        }
    }
}