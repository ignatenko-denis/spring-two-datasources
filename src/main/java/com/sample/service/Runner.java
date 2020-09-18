package com.sample.service;

import com.sample.dto.InputRq;
import com.sample.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class Runner {
    private final Random RANDOM = new Random();

    @Autowired
    private RequestProcessor processor;

    @Scheduled(fixedRateString = "1000")
    public void run() {
        InputRq rq = new InputRq();

        rq.setRqUID(UUID.randomUUID().toString());
        rq.setCode("" + RANDOM.nextInt());
        rq.setStart(DateUtil.toDate("2020-01-01"));
        rq.setEnd(DateUtil.toDate("2020-06-01"));

        processor.receiveRq(rq);
    }
}
