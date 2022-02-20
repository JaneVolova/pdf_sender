create table students (
    id serial primary key,
    first_name varchar(20),
    last_name varchar(20)
);

create table logbook (
    logbook_id serial primary key,
    fio varchar(25),
    date date,
    description varchar(150)
)
