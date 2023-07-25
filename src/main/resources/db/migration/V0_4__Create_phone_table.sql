create table if not exists "phone" (
    id  varchar primary key default uuid_generate_v4(),
    employee_id varchar,
    value varchar unique
);