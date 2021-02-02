-- User with relations included

-- CLEAN UP
DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS users_seq;

-- NEW CREATIONS

CREATE SEQUENCE users_seq;
CREATE TABLE users (
  id int NOT NULL DEFAULT NEXTVAL ('users_seq'),
  name varchar(45) NOT NULL,
  email varchar(45) NOT NULL,
  password varchar(45) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT USERNAME_UNIQUE UNIQUE  (name)
);

ALTER SEQUENCE users_seq RESTART WITH 1;


-- import dummy data
insert into users (name, email, password) values ('hreyes0', 'cbeere0@uiuc.edu', 'EthrW0BMe');
insert into users (name, email, password) values ('umelledy1', 'aklimshuk1@noaa.gov', '1Elok1sGnJR');
insert into users (name, email, password) values ('dmccallum2', 'etale2@mysql.com', 'A7PAQRitT');
insert into users (name, email, password) values ('emargach3', 'sflack3@yellowpages.com', 'sS8MVNn6R');
insert into users (name, email, password) values ('jpigden4', 'pganny4@npr.org', 'IkF0rf70ih');
insert into users (name, email, password) values ('sbratt5', 'nmayworth5@altervista.org', 'k8nMuQa');
insert into users (name, email, password) values ('cpaulitschke6', 'iparnham6@prlog.org', '594OCs');
insert into users (name, email, password) values ('aregenhardt7', 'lsadat7@ovh.net', 'zjQAQF8PRzh');
insert into users (name, email, password) values ('lpenhall8', 'hwestoff8@oracle.com', 'rHphktbe8TYy');
insert into users (name, email, password) values ('amcilreavy9', 'gzorer9@rakuten.co.jp', 'QZAFX4u');
