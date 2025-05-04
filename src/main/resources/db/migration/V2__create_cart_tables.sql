create table cart_items
(
    id       BIGINT auto_increment not null
        primary key,
    cart     BINARY(16) not null,
    product  BIGINT     not null,
    quantity INTEGER    not null
);

create table carts
(
    id           BINARY(16) not null
        primary key,
    date_created DATE       not null
);

alter table cart_items
    add constraint cart_items_carts_id_fk
        foreign key (cart) references carts (id);

alter table cart_items
    add constraint cart_items_products_id_fk
        foreign key (product) references products (id);