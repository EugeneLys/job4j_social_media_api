package ru.job4j.media.service;

import ru.job4j.media.model.Follower;

public interface FollowerService {

    void delete(Follower follower);
    boolean follow(Follower follower);
    int accept(Follower follower);
}
