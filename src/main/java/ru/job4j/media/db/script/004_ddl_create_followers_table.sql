create table followers
(
    id              SERIAL PRIMARY KEY,
    follower_id     INTEGER REFERENCES users(id) ON DELETE CASCADE,
    followed_id     INTEGER REFERENCES users(id) ON DELETE CASCADE
);