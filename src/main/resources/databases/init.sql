CREATE TABLE IF NOT EXISTS types
(
    id serial NOT NULL PRIMARY KEY ,
    type_name TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS products
(
    id SERIAL PRIMARY KEY ,
    type_id bigint unsigned NOT NULL,
    product_name TEXT NOT NULL ,
    price INTEGER ,
    description TEXT ,
    cover_link TEXT,
    FOREIGN KEY (type_id) REFERENCES types (id) ON DELETE CASCADE
    );

create table if not exists users
(
    id serial primary key,
    username text,
    password text,
    role text
);

create table if not exists basket
(
    id serial primary key,
    user_id bigint unsigned,
    product_id bigint unsigned,
    product_count integer,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE
    );