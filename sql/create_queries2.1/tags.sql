-- Tags with relations included

DROP TABLE IF EXISTS tags;
DROP SEQUENCE IF EXISTS tags_seq;

CREATE SEQUENCE tags_seq;

CREATE TABLE tags (
  id int NOT NULL DEFAULT NEXTVAL ('tags_seq'),
  name varchar(45) NOT NULL,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  CONSTRAINT TAG_UNIQUE UNIQUE  (name)
);

ALTER SEQUENCE tags_seq RESTART WITH 1;


-- dummy data
insert into tags (name) values ('Aquamarine');
insert into tags (name) values ('Teal');
insert into tags (name) values ('Mauv');
insert into tags (name) values ('Crimson');
insert into tags (name) values ('Violet');
insert into tags (name) values ('Blue');
insert into tags (name) values ('Yellow');
insert into tags (name) values ('Turquoise');
insert into tags (name) values ('Puce');
