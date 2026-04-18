CREATE TABLE IF NOT EXISTS messages
(
    id              SERIAL PRIMARY KEY,
    sender_id       INTEGER REFERENCES users(id),
    addressee_id    INTEGER REFERENCES users(id),
    title           VARCHAR,
    text            VARCHAR        NOT NULL
);