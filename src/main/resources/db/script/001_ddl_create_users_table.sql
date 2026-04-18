CREATE TABLE IF NOT EXISTS users
(
    id       SERIAL PRIMARY KEY,
    email    VARCHAR UNIQUE NOT NULL,
    name     VARCHAR        NOT NULL,
    password VARCHAR        NOT NULL
);