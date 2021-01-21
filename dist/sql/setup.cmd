@echo off
TITLE Setup Project Blackout SQL.

SET PGBIN="C:\Program Files\PostgreSQL\9.3\bin\psql.exe"
SET PGDATABASE=
SET PGHOST=localhost
SET PGPORT=5432
SET PGUSER=postgres
SET PGPASSWORD=123456

%PGBIN% -f tables\public.sql

pause