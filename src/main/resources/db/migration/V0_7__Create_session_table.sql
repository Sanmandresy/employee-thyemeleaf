create table if not exists "session" (
     id  varchar primary key default uuid_generate_v4(),
     user_id varchar references "user" (id),
     creation_datetime timestamp without time zone default now(),
     validity timestamp default (current_timestamp + interval '1 hour'),
     is_expired boolean default false
);