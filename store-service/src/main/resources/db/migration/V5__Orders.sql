create table if not exists store.orders
(
    id               serial primary key,
    total_price      float,
    order_status     varchar(25) not null default 'PENDING',
    delivery_address varchar(500)
);

create table if not exists store.order_item
(
    item_id          serial primary key,
    quantity    int   not null check ( quantity > 0 ) default 1,
    fixed_price float not null check ( fixed_price > 0),
    dish_id     int   not null,
    order_id    int   not null,

    foreign key (dish_id)
        references store.dish (id),
    foreign key (order_id)
        references store.orders (id) on delete cascade

);