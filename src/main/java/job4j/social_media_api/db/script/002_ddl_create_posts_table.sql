CREATE TABLE posts
(
    id       SERIAL PRIMARY KEY,
    user_id  INTEGER REFERENCES user(id),
    title    VARCHAR,
    text     VARCHAR        NOT NULL
);