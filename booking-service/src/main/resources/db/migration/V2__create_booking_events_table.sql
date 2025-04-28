create sequence booking_event_id_seq start with 1 increment by 50;

create table booking_events
(
    id           bigint default nextval('booking_event_id_seq') not null,
    reservation_number text                                  not null references reservations (reservation_number),
    event_id     text                                         not null unique,
    event_type   text                                         not null,
    payload      text                                         not null,
    created_at   timestamp                                    not null,
    updated_at   timestamp,
    primary key (id)
);