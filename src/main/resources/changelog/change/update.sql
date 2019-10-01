--liquibase formatted sql
--changeset admin:5 runOnChange:true
update accounts
set customer_id=1
where id = 1;

update accounts
set customer_id=2
where id = 2;

update accounts
set customer_id=3
where id = 3;