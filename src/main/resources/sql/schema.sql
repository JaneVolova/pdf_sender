create table logbook (
    logbook_id serial primary key,
    date varchar(20),
    client varchar(20),
    description varchar(150)
)
