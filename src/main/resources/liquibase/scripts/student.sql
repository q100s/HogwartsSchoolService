-- liquibase formatted sql

-- changeset q100s:1
create index students_name_index ON student (name);
