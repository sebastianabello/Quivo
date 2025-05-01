create sequence booking_event_id_seq start with 1 increment by 50;

create table booking_events (
    id bigint default nextval('booking_event_id_seq') not null,
    event_id text not null unique,
    created_at timestamp not null,
    updated_at timestamp,
    primary key (id)
);