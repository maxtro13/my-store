create schema if not exists store;
create table if not exists dish
(
    id           serial primary key,
    name         varchar(55)   not null check ( length(trim(name)) >= 6),
    description  varchar(1024) not null,
    category     varchar(32)   not null,
    availability boolean,
    price        decimal(5, 2)
);