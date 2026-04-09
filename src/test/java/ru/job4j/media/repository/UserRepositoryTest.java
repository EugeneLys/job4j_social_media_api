package ru.job4j.media.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.media.model.Follower;
import ru.job4j.media.model.User;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowerRepository followerRepository;

    @BeforeEach
    public void setUp() {
        followerRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void whenSaveThenFindById() {
        var user = new User("user@user.com", "User1", "password1");
        userRepository.save(user);
        var foundUser = userRepository.findById(user.getId());
        Assertions.assertThat(foundUser).isPresent();
        Assertions.assertThat(foundUser.get().getName()).isEqualTo("User1");
    }

    @Test
    public void whenFindAllThenReturnAll() {
        var user1 = new User("user@user.com", "User1", "password1");
        var user2 = new User("user2@user.com", "User2", "password2");
        userRepository.save(user1);
        userRepository.save(user2);
        var users = userRepository.findAll();
        Assertions.assertThat(users).hasSize(2);
        Assertions.assertThat(users).extracting(User::getName).contains("User1", "User2");
    }

    @Test
    public void whenFindByEmailAndPassword() {
        var user = new User("user@user.com", "User1", "password1");
        userRepository.save(user);
        var found = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
        var wrong = userRepository.findByEmailAndPassword("wrong@wrong.com", "wrong");
        Assertions.assertThat(found).isPresent();
        Assertions.assertThat(wrong).isEmpty();
        Assertions.assertThat(found.get().getName()).isEqualTo("User1");
    }

    @Test
    public void whenFindFollowersById() {
        var user1 = new User("user1@user.com", "User1", "password1");
        var user2 = new User("user2@user.com", "User2", "password2");
        var user3 = new User("user3@user.com", "User3", "password3");
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        followerRepository.save(new Follower(user2, user1));
        followerRepository.save(new Follower(user3, user1));
        var users = userRepository.findFollowersById(user1.getId());
        Assertions.assertThat(users).hasSize(2);
        Assertions.assertThat(users).extracting(User::getName).contains("User2", "User3");
    }

    @Test
    public void whenFindFriendsById() {
        var user1 = new User("user1@user.com", "User1", "password1");
        var user2 = new User("user2@user.com", "User2", "password2");
        var user3 = new User("user3@user.com", "User3", "password3");
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        followerRepository.save(new Follower(user1, user2));
        followerRepository.save(new Follower(user2, user1));
        followerRepository.save(new Follower(user3, user2));
        var users = userRepository.findFriendsById(user1.getId());
        Assertions.assertThat(users).hasSize(1);
        Assertions.assertThat(users).extracting(User::getName).contains("User2");
    }
}
