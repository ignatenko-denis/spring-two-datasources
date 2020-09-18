package com.sample.dao.mssql.entity;

import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.ParameterMode.IN;
import static javax.persistence.ParameterMode.OUT;

@NamedStoredProcedureQueries(
        @NamedStoredProcedureQuery(
                name = ProcedureResult.SP_SAMPLE,
                procedureName = ProcedureResult.SP_SAMPLE,
                parameters = {
                        @StoredProcedureParameter(name = "rq_uid", type = String.class, mode = IN),
                        @StoredProcedureParameter(name = "code", type = String.class, mode = IN),
                        @StoredProcedureParameter(name = "start", type = LocalDate.class, mode = IN),
                        @StoredProcedureParameter(name = "end", type = LocalDate.class, mode = IN),
                        @StoredProcedureParameter(name = "result", type = String.class, mode = OUT),
                        @StoredProcedureParameter(name = "id", type = UUID.class, mode = OUT)
                }
        )
)
@Entity
public class ProcedureResult implements Serializable {
    public static final String SP_SAMPLE = "sp_sample";

    public static final String MESSAGE_FIELD = "result";
    public static final String MESSAGE_SUCCESS = "OK";
    public static final String PROCESS_ID_FIELD = "id";

    /**
     * Unused.
     * Fake field.
     */
    @Getter
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
}
