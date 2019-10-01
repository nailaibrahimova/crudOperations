--liquibase formatted sql
--changeset admin:2 runOnChange:true

alter table customers add column created_at timestamp default now();
alter table customers add column updated_at timestamp default now();

alter table accounts add column customer_id int references customers(id);
alter table accounts add column balance decimal default 0.00;
alter table accounts add column created_at timestamp default now();
alter table accounts add column updated_at timestamp default now();
