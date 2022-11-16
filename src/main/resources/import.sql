/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2022/11/15 13:51:32                          */
/*==============================================================*/


drop table if exists booking;

drop table if exists customer;

drop table if exists flight;

drop table if exists contact;

drop index UKgnqwbwwcn7x0m5jlt4158dass;

/*==============================================================*/
/* Table: booking                                               */
/*==============================================================*/
create table booking
(
    Id           bigint not null,
    customer_id  bigint,
    flight_id    bigint,
    booking_date date,
    primary key (Id)
);

/*==============================================================*/
/* Table: customer                                              */
/*==============================================================*/
create table customer
(
    Id            bigint       not null,
    customer_name varchar(100) not null,
    phone_number  varchar(100) not null,
    email         varchar(100) not null,
    primary key (Id)
);

/*==============================================================*/
/* Table: flight                                                */
/*==============================================================*/
create table flight
(
    Id                 bigint not null,
    number             varchar(100),
    point_of_departure varchar(100),
    destination        varchar(100),
    primary key (Id)
);

create table contact
(
    id           bigint not null,
    birth_date   date,
    email        varchar(255),
    first_name   varchar(255),
    last_name    varchar(255),
    phone_number varchar(255),
    state        varchar(255),
    primary key (id)
);

create table hibernate_sequences
(
    sequence_name varchar(255) not null,
    next_val      bigint,
    primary key (sequence_name)
);

alter table booking
    add constraint FK_Reference_17 foreign key (customer_id)
        references customer (Id) on delete restrict on update restrict;

alter table booking
    add constraint FK_Reference_19 foreign key (flight_id)
        references flight (Id) on delete restrict on update restrict;

alter table contact
    add constraint UKgnqwbwwcn7x0m5jlt4158dass unique (email);

insert into hibernate_sequences(sequence_name, next_val)
values ('default', 0);






