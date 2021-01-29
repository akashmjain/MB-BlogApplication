
-- POSTGRES VERSION 1.1 USER
-- 
-- Table structure for table 'user'
--

DROP TABLE IF EXISTS posts;

CREATE TABLE posts (
  id serial not null,
  title varchar(50) NOT NULL,
  excerpt text NOT NULL,
  content text NOT NULL,
  author varchar(50) NOT NULL,
  published_at date NOT NULL,
  is_published boolean NOT NULL,
  created_at date NOT NULL,
  updated_at date NOT NULL,
  PRIMARY KEY (id)
);

--
-- Dummy data for users
--
insert into posts (title, excerpt, content, author, published_at, is_published, created_at, updated_at) values ('Mac', 'in consequat ut nulla sed accumsan felis ut at dolor quis odio consequat varius integer ac leo', 'lacus at velit vivamus vel nulla eget eros elementum pellentesque quisque porta volutpat erat quisque erat eros viverra eget congue eget semper rutrum nulla nunc purus phasellus in felis donec semper sapien a libero nam dui proin leo odio porttitor id consequat in consequat ut nulla sed accumsan felis ut at dolor quis odio consequat varius integer ac leo pellentesque ultrices mattis odio donec vitae nisi nam ultrices libero non mattis pulvinar nulla pede ullamcorper augue a suscipit nulla elit', 'Boothe Cornthwaite', '2020/11/26', false, '2020/02/15', '2020/02/23');
insert into posts (title, excerpt, content, author, published_at, is_published, created_at, updated_at) values ('Murk (Mørke)', 'ornare consequat lectus in est risus auctor sed tristique in tempus sit amet sem fusce consequat nulla nisl nunc', 'orci mauris lacinia sapien quis libero nullam sit amet turpis elementum ligula vehicula consequat morbi a ipsum integer a nibh in quis justo maecenas rhoncus aliquam lacus morbi quis tortor id nulla ultrices aliquet maecenas leo odio condimentum id luctus nec molestie sed justo pellentesque viverra pede ac diam cras pellentesque volutpat', 'Alphonse Kaveney', '2020/02/24', false, '2020/10/19', '2020/08/05');
insert into posts (title, excerpt, content, author, published_at, is_published, created_at, updated_at) values ('Wonderful Days (a.k.a. Sky Blue)', 'enim blandit mi in porttitor pede justo eu massa donec dapibus duis', 'varius nulla facilisi cras non velit nec nisi vulputate nonummy maecenas tincidunt lacus at velit vivamus vel nulla eget eros elementum pellentesque quisque porta volutpat erat quisque erat eros viverra eget congue eget semper rutrum nulla nunc purus phasellus in felis donec semper sapien a libero nam dui proin leo odio porttitor id consequat in consequat ut nulla sed accumsan felis ut at dolor quis odio consequat varius integer ac leo pellentesque ultrices mattis odio donec vitae nisi nam ultrices libero', 'Deina Vaskov', '2020/11/21', false, '2020/05/23', '2020/03/30');
insert into posts (title, excerpt, content, author, published_at, is_published, created_at, updated_at) values ('Sometimes Happiness, Sometimes Sorrow (Kabhi Khushi Kabhie Gham)', 'enim lorem ipsum dolor sit amet consectetuer adipiscing elit proin interdum mauris non', 'tempus vel pede morbi porttitor lorem id ligula suspendisse ornare consequat lectus in est risus auctor sed tristique in tempus sit amet sem fusce consequat nulla nisl nunc nisl duis bibendum felis sed interdum venenatis turpis enim blandit mi in porttitor pede justo eu massa donec dapibus duis at velit eu est congue elementum in hac habitasse platea dictumst morbi vestibulum velit id pretium iaculis diam erat fermentum justo nec condimentum neque sapien placerat ante nulla justo aliquam quis turpis eget elit', 'Vasili Wyndham', '2020/12/25', true, '2020/08/07', '2020/02/26');
insert into posts (title, excerpt, content, author, published_at, is_published, created_at, updated_at) values ('Never on Sunday (Pote tin Kyriaki)', 'eu interdum eu tincidunt in leo maecenas pulvinar lobortis est', 'luctus et ultrices posuere cubilia curae donec pharetra magna vestibulum aliquet ultrices erat tortor sollicitudin mi sit amet lobortis sapien sapien non mi integer ac neque duis bibendum morbi non quam nec dui luctus rutrum nulla tellus in sagittis dui vel nisl duis ac nibh fusce lacus purus aliquet at feugiat non pretium quis lectus suspendisse potenti in eleifend quam a odio in hac habitasse platea dictumst maecenas ut massa quis augue luctus tincidunt nulla mollis molestie lorem quisque ut erat curabitur gravida nisi', 'Joline Laise', '2020/12/24', false, '2020/11/14', '2020/07/11');