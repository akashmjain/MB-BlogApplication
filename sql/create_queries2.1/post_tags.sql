-- Tags with relations included

DROP TABLE IF EXISTS post_tags;

CREATE TABLE post_tags (
  post_id int NOT NULL,
  tag_id int NOT NULL,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (post_id, tag_id),


  CONSTRAINT FK_POST FOREIGN KEY (post_id) 
  REFERENCES posts (id) 
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  CONSTRAINT FK_TAG FOREIGN KEY (tag_id) 
  REFERENCES tags (id) 
  ON DELETE NO ACTION ON UPDATE NO ACTION
);


CREATE INDEX FK_POST_idx ON post_tags (post_id);

-- dummy data
insert into post_tags (post_id, tag_id) values (1, 1);
insert into post_tags (post_id, tag_id) values (2, 2);
insert into post_tags (post_id, tag_id) values (3, 3);
insert into post_tags (post_id, tag_id) values (4, 4);
insert into post_tags (post_id, tag_id) values (5, 5);
insert into post_tags (post_id, tag_id) values (6, 6);
insert into post_tags (post_id, tag_id) values (7, 7);
insert into post_tags (post_id, tag_id) values (8, 8);
insert into post_tags (post_id, tag_id) values (9, 9);
insert into post_tags (post_id, tag_id) values (2, 1);
insert into post_tags (post_id, tag_id) values (3, 5);
insert into post_tags (post_id, tag_id) values (2, 8);
insert into post_tags (post_id, tag_id) values (5, 3);
insert into post_tags (post_id, tag_id) values (6, 9);
insert into post_tags (post_id, tag_id) values (3, 10);
insert into post_tags (post_id, tag_id) values (5, 10);
insert into post_tags (post_id, tag_id) values (4, 9);
insert into post_tags (post_id, tag_id) values (1, 1);
insert into post_tags (post_id, tag_id) values (7, 2);
insert into post_tags (post_id, tag_id) values (6, 3);
