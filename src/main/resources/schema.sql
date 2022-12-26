create table prices
(
    price_list integer not null primary key,
    brand_id   integer,
    start_date timestamp,
    end_date   timestamp,
    product_id integer,
    priority   integer,
    price      decimal,
    curr       varchar
);
