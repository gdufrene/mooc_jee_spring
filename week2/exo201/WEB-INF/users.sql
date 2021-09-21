
-- users schema
create table users(
  -- 'INTEGER PRIMARY KEY' acts as an autoincrement.
  -- see https://sqlite.org/autoinc.html
  id INTEGER PRIMARY KEY,
  firstname varchar(80),
  lastname varchar(80),
  email varchar(120),
  password varchar(32),
  -- a constraint on unique email is a good idea
  CONSTRAINT chk_mail UNIQUE(email)
);

-- index on email is a really good idea for performance
CREATE INDEX user_mail_idx ON users(email);

-- users data
insert into users
  (firstname,   lastname,   email,                              password  ) 
values
  ('guillaume', 'dufrene',    'guillaume.dufrene@foo.bar', 'eservices'),
  ('lionel',    'seinturier', 'lionel.seinturier@foo.bar', 'eservices');
