-- liquibase formatted sql

-- changeset q100s:2
create index faculties_name_and_color_index ON faculty (name, color);