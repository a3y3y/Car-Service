CREATE SCHEMA IF NOT EXISTS data;

create sequence data.hibernate_sequence start 1 increment 1;

create table data.cars (
    id int4 not null,
    body_type varchar(255),
    color varchar(255),
    make varchar(255),
    model varchar(255),
    production_date date,
    uuid bytea not null,
    primary key (id)
    );

create table data.client_role (
    client_id int4 not null,
    roles varchar(255)
    );

create table data.clients (
    id int4 not null,
    login varchar(255) not null,
    password varchar(255) not null,
    uuid bytea not null,
    primary key (id)
    );

alter table data.cars
    add constraint UK_cars_uuid unique (uuid);

alter table data.clients
    add constraint UK_clients_login unique (login);

alter table data.clients
    add constraint UK_clients_uuid unique (uuid);

alter table data.client_role
    add constraint FK_clients_role
    foreign key (client_id)
    references data.clients;


