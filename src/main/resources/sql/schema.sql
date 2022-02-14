create table users (
    user_id serial primary key,
    name varchar(30) not null ,
    password varchar(20) not null
);

create table logbook (
    logbook_id serial primary key,
    user_id bigserial,
    date date,
    description varchar(150)
)
