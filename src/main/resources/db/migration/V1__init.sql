-- initialize the tables
create table category (
  category_id bigint(20) not null auto_increment,
  name varchar(255),
  preapproved boolean,
  primary key (category_id)
);

create table seller (
  seller_id bigint(20) not null auto_increment,
  first_name varchar(255),
  last_name varchar(255),
  enrolled boolean,
  primary key (seller_id)
);

create table item (
  item_id bigint(20) not null auto_increment,
  category_id int,
  price decimal(10, 2),
  seller_id int,
  title varchar(255),
  primary key (item_id)
);

create table minimum_price (
  id bigint(20) not null auto_increment,
  category_id int,
  minimum_price decimal(10, 2),
  primary key (id)
)
