-- User with relations included

-- CLEAN UP
DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS users_seq;

-- NEW CREATIONS

CREATE SEQUENCE users_seq;
CREATE TABLE users (
  id int NOT NULL DEFAULT NEXTVAL ('users_seq'),
  name varchar(45) DEFAULT NULL,
  email varchar(45) DEFAULT NULL,
  password varchar(45) DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT USERNAME_UNIQUE UNIQUE  (name)
);

ALTER SEQUENCE users_seq RESTART WITH 1;


