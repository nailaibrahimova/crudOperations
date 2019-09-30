--liquibase formatted sql
--changeset admin:1 runOnChange:true
create table if not exists customers
(
    id   serial primary key,
    name varchar(255)
);

create table if not exists accounts
(
    id       serial primary key,
    login    varchar(255),
    password varchar(255)
);