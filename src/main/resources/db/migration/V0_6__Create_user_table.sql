create table if not exists "user" (
     id  varchar primary key default uuid_generate_v4(),
     username varchar,
     password varchar
);