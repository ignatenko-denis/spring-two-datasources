-- create exist schema on test MS SQL Server

USE sample;
GO

CREATE TABLE dbo.sample_log
(
    id         INT PRIMARY KEY IDENTITY (1, 1),
    rq_uid     VARCHAR(255) NOT NULL,
    code       VARCHAR(255) NOT NULL,
    start      DATE,
    "end"      DATE,
    created_at DATETIME     NOT NULL
)
GO;
--------
CREATE TYPE XTYPE FROM VARCHAR(255) NULL;
GO;

CREATE PROCEDURE dbo.sp_sample @rq_uid VARCHAR(255) = NULL,
                               @code VARCHAR(40) = NULL,
                               @start SMALLDATETIME,
                               @end SMALLDATETIME,
                               @result XTYPE = NULL OUT,
                               @id UNIQUEIDENTIFIER = NULL OUT
AS
BEGIN
    INSERT INTO dbo.sample_log (rq_uid, code, start, "end", created_at)
    VALUES (@rq_uid, @code, @start, @end, GETDATE());

    SET @result = 'OK';
    SET @id = NEWID();
END;
GO
--------
DECLARE @start SMALLDATETIME = '2020-01-01 12:43:10';
DECLARE @end SMALLDATETIME = '2020-05-01 12:43:10';
DECLARE @code VARCHAR(40) = 'XCODE';
DECLARE @result XTYPE = NULL;
DECLARE @id UNIQUEIDENTIFIER = null;
DECLARE @rq_uid VARCHAR(255) = '1234468487648763';

EXECUTE dbo.sp_sample @rq_uid = @rq_uid,
        @code = @code,
        @start = @start,
        @end = @end,
        @result = @result OUT,
        @id = @id OUT

print @result
print @id
GO