package ru.job4j.media.service;

import ru.job4j.media.model.Follower;
import ru.job4j.media.repository.FollowerRepository;

public class SimpleFollowerService implements FollowerService {

    private final FollowerRepository followerRepository;

    public SimpleFollowerService(FollowerRepository followerRepository) {
        this.followerRepository = followerRepository;
    }

    @Override
    public void delete(Follower follower) {
        followerRepository.delete(follower);
    }

    @Override
    public boolean follow(Follower follower) {
        return followerRepository.save(follower) != null;
    }

    @Override
    public int accept(Follower follower) {
        return followerRepository.save(new Follower(follower.getFollowed(), follower.getFollower())).getId();
    }
}
