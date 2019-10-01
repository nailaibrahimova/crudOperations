--liquibase formatted sql
--changeset admin:4 runOnChange:true

select *
from customers;

select *
from accounts;