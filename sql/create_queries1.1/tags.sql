
-- POSTGRES VERSION 1.1 USER
-- 
-- Table structure for table 'tags'

DROP TABLE IF EXISTS tags;

CREATE TABLE tags (
  id serial not null,
  name varchar(50) NOT NULL,
  created_at date NOT NULL,
  updated_at date NOT NULL,
  PRIMARY KEY (id)
);

--
-- Dummy Data for tags
--

insert into tags (name, created_at, updated_at) values ('etiam', '2020/02/10', '2020/10/13');
insert into tags (name, created_at, updated_at) values ('bibendum', '2020/08/20', '2020/04/16');
insert into tags (name, created_at, updated_at) values ('donec', '2020/02/22', '2020/12/31');
insert into tags (name, created_at, updated_at) values ('bibendum', '2020/10/24', '2020/08/09');
insert into tags (name, created_at, updated_at) values ('accumsan', '2020/12/07', '2020/11/01');