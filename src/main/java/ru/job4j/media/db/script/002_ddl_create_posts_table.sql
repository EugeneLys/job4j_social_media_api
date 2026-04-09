CREATE TABLE posts
(
    id        SERIAL PRIMARY KEY,
    user_id   INTEGER REFERENCES users(id),
    title     VARCHAR,
    text      VARCHAR        NOT NULL,
    createdAt DATE,
    file_id   INTEGER REFERENCES files(id)
);