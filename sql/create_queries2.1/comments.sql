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
