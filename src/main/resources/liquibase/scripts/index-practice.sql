-- liquibase formatted sql

-- changeset q100s:1
create index students_name_index ON student (name);

-- changeset q100s:2
create index faculties_name_and_color_index ON faculty (name, color);