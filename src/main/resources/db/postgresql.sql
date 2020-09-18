CREATE SCHEMA IF NOT EXISTS sample AUTHORIZATION "sample";
SET search_path = sample;
GRANT ALL ON ALL TABLES IN SCHEMA sample TO "sample";
ALTER DEFAULT PRIVILEGES IN SCHEMA sample GRANT ALL ON TABLES TO "sample";
GRANT SELECT ON ALL TABLES IN SCHEMA sample TO "sample";
ALTER DEFAULT PRIVILEGES IN SCHEMA sample GRANT SELECT ON TABLES TO "sample";

--------------------

CREATE TABLE IF NOT EXISTS request
(
    id       BIGSERIAL PRIMARY KEY       NOT NULL,
    rq_uid   VARCHAR(255)                NOT NULL,
    code     VARCHAR(255)                NOT NULL,
    start    DATE                        NOT NULL,
    "end"    DATE                        NOT NULL,
    received TIMESTAMP(3) WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status   VARCHAR(60)                 NOT NULL,
    CONSTRAINT start_less_end CHECK (start <= "end"),
    CONSTRAINT status_in_set CHECK (status IN ('ERROR', 'RECEIVED', 'SENT'))
);