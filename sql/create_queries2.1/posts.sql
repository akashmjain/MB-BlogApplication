-- User with relations included

DROP TABLE IF EXISTS psots;

CREATE SEQUENCE posts_seq;

CREATE TABLE posts (
  id int NOT NULL DEFAULT NEXTVAL ('posts_seq'),
  title text NOT NULL,
  excerpt text NOT NULL,
  content text NOT NULL,
  user_id int NOT NULL,
  published_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
  is_published boolean NOT NULL,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),

  CONSTRAINT FK_USER_ID 
  FOREIGN KEY (user_id) 
  REFERENCES users (id) 
  ON DELETE NO ACTION ON UPDATE NO ACTION

);

ALTER SEQUENCE posts_seq RESTART WITH 1;

CREATE INDEX FK_USER_idx ON posts (user_id);
