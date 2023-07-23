create extension if not exists "uuid-ossp";

create table if not exists "employee" (
    id              varchar primary key default uuid_generate_v4(),
    registration_number varchar,
    first_name      varchar,
    last_name       varchar    not null,
    birth_date      date       not null,
    address varchar,
    phone varchar,
    email varchar,
    gender char(1),
    identity varchar,
    position varchar,
    children integer default 0,
    started_at timestamp without time zone default now(),
    departed_at timestamp without time zone default now(),
    category varchar,
    cnaps varchar,
    image bytea
);