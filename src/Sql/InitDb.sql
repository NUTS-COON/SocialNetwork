CREATE DATABASE social_network
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'English_United States.1252'
    LC_CTYPE = 'English_United States.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

GRANT ALL ON DATABASE social_network TO PUBLIC;

GRANT ALL ON DATABASE social_network TO postgres;


create table if not exists person
(
    id       serial       not null,
    email    varchar(100) not null,
    password varchar(500) not null,
    name     varchar(50)  not null,
    surname  varchar(50),
    is_male  boolean,
    birthday date,
    phone    varchar(20),
    constraint person_pk
        primary key (id)
);

alter table person
    owner to postgres;

create unique index if not exists person_email_uindex
    on person (email);

create unique index if not exists person_id_uindex
    on person (id);

create table if not exists person_token
(
    id        serial not null,
    token     varchar(100),
    person_id integer,
    constraint person_token_pk
        primary key (id),
    constraint person_token_person_id_fk
        foreign key (person_id) references person
            on delete cascade
);

alter table person_token
    owner to postgres;

create unique index if not exists person_token_id_uindex
    on person_token (id);

create unique index if not exists person_token_token_uindex
    on person_token (token);

create table if not exists public_message
(
    id        serial  not null,
    person_id integer not null,
    text      varchar,
    date      timestamp,
    constraint public_message_pk
        primary key (id)
);

alter table public_message
    owner to postgres;

create unique index if not exists public_message_id_uindex
    on public_message (id);

create table if not exists chat
(
    id serial not null,
    constraint chat_pk
        primary key (id)
);

alter table chat
    owner to postgres;

create unique index if not exists chat_id_uindex
    on chat (id);

create table if not exists person_to_chat
(
    id        serial  not null,
    person_id integer not null,
    chat_id   integer not null,
    constraint person_to_chat_pk
        primary key (id),
    constraint person_to_chat_chat_id_fk
        foreign key (chat_id) references chat,
    constraint person_to_chat_person_id_fk
        foreign key (person_id) references person
);

alter table person_to_chat
    owner to postgres;

create unique index if not exists person_to_chat_id_uindex
    on person_to_chat (id);

create table if not exists message
(
    id        serial                  not null,
    chat_id   integer                 not null,
    text      varchar                 not null,
    datetime  timestamp default now() not null,
    sender_id integer                 not null,
    constraint message_pk
        primary key (id),
    constraint message_chat_id_fk
        foreign key (chat_id) references chat,
    constraint message_person_id_fk
        foreign key (sender_id) references person
);

alter table message
    owner to postgres;

create unique index if not exists message_id_uindex
    on message (id);

create table if not exists public
(
    id          serial       not null,
    name        varchar(100) not null,
    description varchar,
    created     timestamp    not null,
    constraint public_pk
        primary key (id)
);

alter table public
    owner to postgres;

create unique index if not exists public_id_uindex
    on public (id);

create table if not exists person_to_public_relation
(
    id        serial  not null,
    person_id integer not null,
    public_id integer not null,
    relation  integer not null,
    constraint person_tu_public_relation_pk
        primary key (id),
    constraint person_tu_public_relation_person_id_fk
        foreign key (person_id) references person,
    constraint person_tu_public_relation_public_id_fk
        foreign key (public_id) references public
);

alter table person_to_public_relation
    owner to postgres;

create unique index if not exists person_tu_public_relation_id_uindex
    on person_to_public_relation (id);

create table if not exists public_post
(
    id           serial    not null,
    author       integer   not null,
    text         varchar,
    is_published boolean   not null,
    published    timestamp,
    created      timestamp not null,
    public_id    integer   not null,
    constraint public_post_pk
        primary key (id),
    constraint public_post_person_id_fk
        foreign key (author) references person,
    constraint public_post_public_id_fk
        foreign key (public_id) references public
);

alter table public_post
    owner to postgres;

create unique index if not exists public_post_id_uindex
    on public_post (id);

create table if not exists person_relationship
(
    id             serial  not null,
    first_person   integer not null,
    second_person  integer not null,
    relationship   integer not null,
    last_initiator integer not null,
    constraint person_relationship_pk
        primary key (id),
    constraint person_relationship_person_id_fk
        foreign key (first_person) references person,
    constraint person_relationship_person_id_fk_2
        foreign key (second_person) references person,
    constraint person_relationship_person_id_fk_3
        foreign key (last_initiator) references person
);

alter table person_relationship
    owner to postgres;

create unique index if not exists person_relationship_id_uindex
    on person_relationship (id);

