package com.sample.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class InputRq {
    private String rqUID;
    private String code;
    private LocalDate start;
    private LocalDate end;
}