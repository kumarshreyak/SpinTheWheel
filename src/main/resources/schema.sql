CREATE TABLE IF NOT EXISTS Pokemon(
    id INTEGER AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    url VARCHAR(255),
    primary key (id)
);

INSERT into Pokemon(id, name, url)
values (1, 'Pikachu', 'pikachu.com')

