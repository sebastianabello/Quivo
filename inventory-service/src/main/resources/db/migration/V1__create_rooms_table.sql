    create sequence room_id_seq start with 1 increment by 50;

create table rooms
(
    id bigint default nextval('room_id_seq') not null,
    code        text not null unique,
    name        text not null,
    description text,
    image_url   text,
    price       numeric not null,
    primary key (id)
);