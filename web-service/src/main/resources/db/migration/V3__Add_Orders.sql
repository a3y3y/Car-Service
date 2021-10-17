create table data.orders (
    id int4 not null,
    client_id int4,
    car_id int4,
    rent_start date,
    rent_end date,
    order_date date,
    status varchar(255),
    uuid bytea not null,
    primary key (id)
    );

alter table data.orders
    add constraint UK_orders_uuid unique (uuid);

alter table data.orders
    add constraint FK_orders_clients
    foreign key (client_id)
    references data.clients;

alter table data.orders
    add constraint FK_orders_cars
    foreign key (car_id)
    references data.cars;             



