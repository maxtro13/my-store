
alter table store.dish
    add column image_id bigint;

-- alter table store.dish
--     add constraint fk_dish_image
--         foreign key (image_id)
--             references store.images
--             on delete cascade;
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