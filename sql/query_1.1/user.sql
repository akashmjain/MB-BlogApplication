
-- POSTGRES VERSION 1.1 USER
-- 
-- Table structure for table 'user'
--

DROP SEQUENCE IF EXISTS user_seq;

DROP TABLE IF EXISTS users;

CREATE SEQUENCE user_seq;

CREATE TABLE users (
  id int NOT NULL DEFAULT NEXTVAL ('user_seq'),
  name varchar(50) NOT NULL,
  email varchar(50) NOT NULL,
  password varchar(10) NOT NULL,
  PRIMARY KEY (id)
);

ALTER SEQUENCE student_seq RESTART WITH 1;
--
-- Dummy data for users
--
INSERT INTO users(name, email, password) VALUES ('Akash', 'akash@akashmjain.com', 'akash123'), ('John', 'john@doe.com', 'john123'), ('Carter', 'carter@smith.com', 'carter123'), ('Hana', 'hana@matsuri.com', 'hana123'), ('Yuri', 'yuri@kawasaki.com', 'yuri123');


