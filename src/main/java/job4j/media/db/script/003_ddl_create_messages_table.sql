CREATE TABLE messages
(
    id              SERIAL PRIMARY KEY,
    sender_id       INTEGER REFERENCES user(id),
    addressee_id    INTEGER REFERENCES user(id),
    title           VARCHAR,
    text            VARCHAR        NOT NULL
);