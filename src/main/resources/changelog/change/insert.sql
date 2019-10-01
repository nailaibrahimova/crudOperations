--liquibase formatted sql
--changeset admin:3 runOnChange:true

insert into customers (name)
values ('Sabina');
insert into customers (name)
values ('Tom');
insert into customers (name)
values ('Naila');

insert into accounts (login, password)
values ('hajimurad', 'saji1999');
insert into accounts (login, password)
values ('tomcat', 'tom25e21');
insert into accounts (login, password)
values ('ibrahimova', 'naila123');