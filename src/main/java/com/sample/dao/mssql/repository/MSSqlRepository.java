package com.sample.dao.mssql.repository;

import com.sample.dao.mssql.entity.ProcedureResult;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Map;

import static com.sample.dao.mssql.entity.ProcedureResult.SP_SAMPLE;

@Repository
public interface MSSqlRepository extends CrudRepository<ProcedureResult, Long> {
    @Transactional
    @Procedure(name = SP_SAMPLE)
    Map<String, Object> callStoredProcedure(@Param("rq_uid") String rqUid,
                                            @Param("code") String code,
                                            @Param("start") LocalDate start,
                                            @Param("end") LocalDate end);
}
