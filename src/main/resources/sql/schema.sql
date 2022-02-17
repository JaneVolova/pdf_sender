create table students (
    id serial primary key,
    first_name varchar(20),
    last_name varchar(20)
);

create table logbook (
    logbook_id serial primary key,
    student_id serial,
    date date,
    description varchar(150)
)
