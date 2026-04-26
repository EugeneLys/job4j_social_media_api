package ru.job4j.media.service;

import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.job4j.media.model.Follower;
import ru.job4j.media.repository.FollowerRepository;

@Transactional
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
        followerRepository.save(follower);
        return true;
    }

    @Override
    public int accept(Follower follower) {
        return followerRepository.save(new Follower(follower.getFollowed(), follower.getFollower())).getId();
    }
}
