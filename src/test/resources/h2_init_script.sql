CREATE SCHEMA IF NOT EXISTS data;
CREATE TABLE data.cars (
    id integer NOT NULL auto_increment,
    make character varying,
    model character varying,
    body_type character varying,
    color character varying,
    uuid uuid,
    production_date date
);