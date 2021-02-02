-- comment with relations included

DROP TABLE IF EXISTS comments;

CREATE SEQUENCE comments_seq;

CREATE TABLE comments (
  id int NOT NULL DEFAULT NEXTVAL ('comments_seq'),
  name varchar(45) NOT NULL,
  email varchar(45) NOT NULL,
  comment text NOT NULL,
  post_id int NOT NULL,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  
  CONSTRAINT FK_POST_COMMENT
  FOREIGN KEY (post_id) 
  REFERENCES posts (id) 
  
  ON DELETE NO ACTION ON UPDATE NO ACTION
);



ALTER SEQUENCE comments_seq RESTART WITH 1;

CREATE INDEX FK_POST_COMMENT_idx ON comments (post_id);

-- dummy data
insert into comments (name, email, comment, post_id) values ('Luca Knocker', 'lknocker0@cpanel.net', 'Nullam orci pede, venenatis non, sodales sed, tincidunt eu, felis. Fusce posuere felis sed lacus.', 1);
insert into comments (name, email, comment, post_id) values ('Mortie Dagworthy', 'mdagworthy1@examiner.com', 'Sed accumsan felis. Ut at dolor quis odio consequat varius. Integer ac leo. Pellentesque ultrices mattis odio.', 2);
insert into comments (name, email, comment, post_id) values ('Hart Grimsdike', 'hgrimsdike2@domainmarket.com', 'Fusce posuere felis sed lacus. Morbi sem mauris, laoreet ut, rhoncus aliquet, pulvinar sed, nisl.', 3);
insert into comments (name, email, comment, post_id) values ('Urban Wyleman', 'uwyleman3@fda.gov', 'Nam nulla. Integer pede justo, lacinia eget, tincidunt eget, tempus vel, pede.', 4);
insert into comments (name, email, comment, post_id) values ('Mariejeanne Vinnick', 'mvinnick4@techcrunch.com', 'Sed sagittis. Nam congue, risus semper porta volutpat, quam pede lobortis ligula, sit amet eleifend pede libero quis orci.', 1);
insert into comments (name, email, comment, post_id) values ('Ange Siggens', 'asiggens5@thetimes.co.uk', 'Integer aliquet, massa id lobortis convallis, tortor risus dapibus augue, vel accumsan tellus nisi eu orci. Mauris lacinia sapien quis libero.', 4);
insert into comments (name, email, comment, post_id) values ('Samara Jacks', 'sjacks6@businessinsider.com', 'Ut tellus. Nulla ut erat id mauris vulputate elementum. Nullam varius.', 7);
insert into comments (name, email, comment, post_id) values ('Jean Wooler', 'jwooler7@sciencedaily.com', 'Quisque ut erat. Curabitur gravida nisi at nibh. In hac habitasse platea dictumst. Aliquam augue quam, sollicitudin vitae, consectetuer eget, rutrum at, lorem.', 8);
insert into comments (name, email, comment, post_id) values ('Martina Cooke', 'mcooke8@angelfire.com', 'Nam nulla. Integer pede justo, lacinia eget, tincidunt eget, tempus vel, pede. Morbi porttitor lorem id ligula. Suspendisse ornare consequat lectus.', 9);
insert into comments (name, email, comment, post_id) values ('Betteanne Gaskin', 'bgaskin9@webnode.com', 'Praesent id massa id nisl venenatis lacinia. Aenean sit amet justo. Morbi ut odio.', 10);
insert into comments (name, email, comment, post_id) values ('Husein Allcock', 'hallcocka@freewebs.com', 'Duis bibendum. Morbi non quam nec dui luctus rutrum.', 2);
insert into comments (name, email, comment, post_id) values ('Michele Mapplethorpe', 'mmapplethorpeb@mapy.cz', 'In blandit ultrices enim. Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Proin interdum mauris non ligula pellentesque ultrices.', 1);
insert into comments (name, email, comment, post_id) values ('Lenci Bettenson', 'lbettensonc@devhub.com', 'Vestibulum ac est lacinia nisi venenatis tristique. Fusce congue, diam id ornare imperdiet, sapien urna pretium nisl, ut volutpat sapien arcu sed augue. Aliquam erat volutpat.', 5);
insert into comments (name, email, comment, post_id) values ('Otes Jenkyn', 'ojenkynd@alibaba.com', 'Morbi vel lectus in quam fringilla rhoncus. Mauris enim leo, rhoncus sed, vestibulum sit amet, cursus id, turpis. Integer aliquet, massa id lobortis convallis, tortor risus dapibus augue, vel accumsan tellus nisi eu orci.', 6);
insert into comments (name, email, comment, post_id) values ('Coriss Peedell', 'cpeedelle@craigslist.org', 'Nulla tempus. Vivamus in felis eu sapien cursus vestibulum. Proin eu mi.', 7);
insert into comments (name, email, comment, post_id) values ('Priscilla Corneck', 'pcorneckf@scientificamerican.com', 'Integer ac neque. Duis bibendum. Morbi non quam nec dui luctus rutrum.', 1);
insert into comments (name, email, comment, post_id) values ('Andreana Shacklady', 'ashackladyg@51.la', 'In est risus, auctor sed, tristique in, tempus sit amet, sem. Fusce consequat. Nulla nisl.', 2);
insert into comments (name, email, comment, post_id) values ('Elita Redley', 'eredleyh@elpais.com', 'Praesent lectus. Vestibulum quam sapien, varius ut, blandit non, interdum in, ante. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Duis faucibus accumsan odio.', 5);
insert into comments (name, email, comment, post_id) values ('Philly Le Teve', 'plei@loc.gov', 'Fusce posuere felis sed lacus. Morbi sem mauris, laoreet ut, rhoncus aliquet, pulvinar sed, nisl. Nunc rhoncus dui vel sem. Sed sagittis.', 4);
insert into comments (name, email, comment, post_id) values ('Merrielle Coventry', 'mcoventryj@blogger.com', 'Phasellus id sapien in sapien iaculis congue. Vivamus metus arcu, adipiscing molestie, hendrerit at, vulputate vitae, nisl. Aenean lectus.', 10);
