-- Enter these in a STRSQL session or similar.

CREATE TABLE users(
 name VARCHAR(12) NOT NULL,
 surname VARCHAR(15) NOT NULL,
 username VARCHAR(12) NOT NULL,
 password VARCHAR(50) NOT NULL
)

INSERT INTO injbrscl1/users (name, surname, username, password)
 VALUES ('Humberto', 'Maturana', 'hmaturana', '12345'),
 ('Claudio', 'Arrau', 'carrau', '123')