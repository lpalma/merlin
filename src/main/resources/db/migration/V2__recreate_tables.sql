DROP TABLE IF EXISTS merlin.commitments;
DROP TABLE IF EXISTS merlin.craftspeople;
DROP TABLE IF EXISTS merlin.projects;

DROP SEQUENCE IF EXISTS merlin.craftspeople_seq;
DROP SEQUENCE IF EXISTS merlin.projects_seq;
DROP SEQUENCE IF EXISTS merlin.craftspeople_seq;

CREATE SEQUENCE IF NOT EXISTS merlin.craftspeople_seq;

CREATE TABLE IF NOT EXISTS merlin.commitments (
    id varchar(255) PRIMARY KEY,
    craftsperson_id varchar(255),
    project_id varchar(255),
    start_date date,
    end_date date
);

