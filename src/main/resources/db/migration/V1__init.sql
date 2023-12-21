create table if not exists roles
(
    id   bigint      not null primary key auto_increment unique,
    name varchar(30) not null
);

create table if not exists users
(
    id       bigint         not null primary key auto_increment unique,
    username varchar(30)    not null,
    password varchar(15000) not null
);

create table if not exists orders
(
    id                    bigint   not null primary key auto_increment unique,
    paid                  boolean  not null,
    modification_datetime datetime not null,
    user_id               bigint   not null,
    foreign key (user_id) references users (id)
);

create table if not exists ordered_items
(
    id                bigint not null primary key auto_increment unique,
    price             int    not null,
    quantity          int    not null,
    order_id          bigint not null,
    available_item_id bigint not null,
    foreign key (order_id) references orders (id)
);

create table if not exists available_items
(
    id       bigint      not null primary key auto_increment unique,
    name     varchar(30) not null,
    quantity int         not null,
    price    int         not null
);



CREATE TABLE if not exists users_roles
(
    user_id bigint not null,
    role_id bigint not null,
    primary key (user_id, role_id),
    foreign key (user_id) references users (id),
    foreign key (role_id) references roles (id)
);


insert into roles (name)
values ('ROLE_USER'),
       ('ROLE_ADMIN');

insert into users (username, password)
values ('user', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i'),
       ('admin', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i');

insert into users_roles (user_id, role_id)
values (1, 1),
       (2, 2);