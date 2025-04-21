create table if not exists store.images
(
    id           serial primary key,
    file_name    varchar not null unique,
    o_file_name  varchar not null,
    size         bigint,
    url          varchar(2048),
    content_type varchar,
    file_path    varchar
);