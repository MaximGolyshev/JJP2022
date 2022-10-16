CREATE TABLE player (
 id int8 PRIMARY KEY,
 nickname varchar(50) unique
);

CREATE TABLE item (
   id int8 PRIMARY KEY unique not null,
   resource_id int8,
   count int4,
   level int4,
   check ( count >= 0 and level >= 0 )
);


CREATE TABLE progress(
   id int8 primary key unique not null,
   player_id int8 references player(id),
   resource_id int8,
   score int4,
   max_score int4
);

CREATE TABLE currency(
   id int8 primary key unique not null,
   resource_id int8,
   name varchar(100),
   count int4,
   check ( count >= 0 )
);

CREATE TABLE player_currency(
    player_id int8 references player(id),
    currency_id int8 references currency(id)
);

CREATE TABLE player_item(
    player_id int8 references player(id),
    item_id int8 references item(id)
)