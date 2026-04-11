package ru.job4j.media.repository;

import ru.job4j.media.model.Follower;
import ru.job4j.media.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.media.service.FollowerService;
import ru.job4j.media.service.SimpleFollowerService;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FollowerRepositoryTest {

    @Autowired
    private FollowerRepository followerRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        followerRepository.deleteAll();
    }

    @Test
    public void whenSaveThenFindById() {
        var user = new User("user@user.com", "User1", "password1");
        var user2 = new User("user2@user.com", "User2", "password2");
        userRepository.save(user);
        userRepository.save(user2);
        var follower = new Follower();
        follower.setFollower(user);
        follower.setFollowed(user2);
        followerRepository.save(follower);
        var found = followerRepository.findById(follower.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getFollower()).isEqualTo(user);
    }

    @Test
    public void whenFindAllThenReturnAll() {
        var user1 = new User("user@user.com", "User1", "password1");
        var user2 = new User("user2@user.com", "User2", "password2");
        userRepository.save(user1);
        userRepository.save(user2);
        var f1 = new Follower(user1, user2);
        var f2 = new Follower(user2, user1);
        followerRepository.save(f1);
        followerRepository.save(f2);
        var followers = followerRepository.findAll();
        assertThat(followers).hasSize(2);
        assertThat(followers).extracting(Follower::getFollower).contains(user1, user2);
    }

    @Test
    public void whenAcceptThenFindById() {
        var user1 = new User("user@user.com", "User1", "password1");
        var user2 = new User("user2@user.com", "User2", "password2");
        userRepository.save(user1);
        userRepository.save(user2);
        Follower f1 = new Follower(user1, user2);
        FollowerService followerService = new SimpleFollowerService(followerRepository);
        followerService.follow(f1);
        assertThat(followerRepository.findAll()).hasSize(1);
        int id = followerService.accept(f1);
        assertThat(followerRepository.findAll()).hasSize(2);
        Follower check = followerRepository.findById(id).get();
        assertThat(check.getFollower()).isEqualTo(user2);
        assertThat(check.getFollowed()).isEqualTo(user1);
    }
}