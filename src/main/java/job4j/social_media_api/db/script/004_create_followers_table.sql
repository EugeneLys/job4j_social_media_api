create table followers
(
    user_id         INTEGER REFERENCES user(id),
    follower_id     INTEGER REFERENCES user(id)
);