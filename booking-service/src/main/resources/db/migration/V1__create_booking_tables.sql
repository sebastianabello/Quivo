-- Secuencias
create sequence reservation_id_seq start with 1 increment by 50;
create sequence reservation_item_id_seq start with 1 increment by 50;

-- Tabla de reservas
create table reservations (
    id                   bigint default nextval('reservation_id_seq') not null,
    reservation_number   text not null unique,
    username             text not null,
    customer_name        text not null,
    customer_email       text not null,
    customer_phone       text not null,
    check_in_date        date not null,
    check_out_date       date not null,
    status               text not null,
    comments             text,
    created_at           timestamp default now(),
    updated_at           timestamp default now(),
    primary key (id)
);

create table reservations_items(
    id       bigint default nextval('reservation_item_id_seq') not null,
    code     text not null,
    name     text not null,
    price    numeric not null,
    guest    integer not null,
    primary key (id),
    reservations_id bigint  not null references reservations (id)
);
