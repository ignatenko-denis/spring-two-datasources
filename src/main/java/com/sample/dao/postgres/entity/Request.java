package com.sample.dao.postgres.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.OffsetDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "request")
@Data
public class Request implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "rq_uid", nullable = false)
    private String rqUid;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "start", nullable = false)
    private LocalDate start;

    @Column(name = "\"end\"", nullable = false)
    private LocalDate end;

    @Column(name = "received", nullable = false)
    private OffsetDateTime received;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;
}
