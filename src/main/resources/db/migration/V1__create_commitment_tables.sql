CREATE SEQUENCE merlin.commitments_seq;
CREATE SEQUENCE merlin.craftspeople_seq;
CREATE SEQUENCE merlin.projects_seq;

CREATE TABLE IF NOT EXISTS merlin.craftspeople (
    id integer PRIMARY KEY,
    name character varying(255)
);

CREATE TABLE IF NOT EXISTS merlin.projects (
    id integer PRIMARY KEY,
    name character varying(255)
);

CREATE TABLE IF NOT EXISTS merlin.commitments (
    id integer PRIMARY KEY,
    craftsperson_id integer REFERENCES merlin.craftspeople(id),
    project_id integer REFERENCES merlin.projects(id),
    start_date date,
    end_date date
);
