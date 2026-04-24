package ru.job4j.media.service;

import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.job4j.media.model.Follower;
import ru.job4j.media.repository.FollowerRepository;

@Validated
@Transactional
public class SimpleFollowerService implements FollowerService {

    private final FollowerRepository followerRepository;

    public SimpleFollowerService(FollowerRepository followerRepository) {
        this.followerRepository = followerRepository;
    }

    @Override
    public void delete(@Valid Follower follower) {
        followerRepository.delete(follower);
    }

    @Override
    public boolean follow(@Valid Follower follower) {
        return followerRepository.save(follower) != null;
    }

    @Override
    public int accept(@Valid Follower follower) {
        return followerRepository.save(new Follower(follower.getFollowed(), follower.getFollower())).getId();
    }
}
