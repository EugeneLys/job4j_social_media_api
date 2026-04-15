package ru.job4j.media.repository;

import ru.job4j.media.model.File;
import ru.job4j.media.model.Follower;
import ru.job4j.media.model.Post;
import ru.job4j.media.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FollowerRepository followerRepository;

    @Autowired
    private MessageRepository messageRepository;

    @BeforeEach
    public void setUp() {
        followerRepository.deleteAll();
        postRepository.deleteAll();
        messageRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void whenSaveThenFindById() {
        User user = new User("user@users.com", "User1", "password");
        userRepository.save(user);
        var myPost = new Post();
        myPost.setAuthor(user);
        myPost.setTitle("post1");
        myPost.setText("This is my post");
        postRepository.save(myPost);
        var found = postRepository.findById(myPost.getId());
        Assertions.assertThat(found).isPresent();
        Assertions.assertThat(found.get().getTitle()).isEqualTo("post1");
    }

    @Test
    public void whenFindAllThenReturnAll() {
        User user = new User("user@users.com", "User1", "password");
        userRepository.save(user);
        var myPost = new Post(user, "post1", "This is my post", LocalDateTime.now(), null);
        var hisPost = new Post(user, "post3", "This is his post", LocalDateTime.now(), null);
        postRepository.save(myPost);
        postRepository.save(hisPost);
        var posts = postRepository.findAll();
        Assertions.assertThat(posts).hasSize(2);
        Assertions.assertThat(posts).extracting(Post::getText).contains("This is my post", "This is his post");
    }

    @Test
    public void whenFindByAuthor() {
        User user = new User("user@users.com", "User1", "password");
        User user2 = new User("user2@users.com", "User2", "password2");
        userRepository.save(user);
        userRepository.save(user2);
        var first = new Post(user, "post1", "This is user1 post", LocalDateTime.now(), null);
        var second = new Post(user, "post2", "This is another user1 post", LocalDateTime.now(), null);
        var third = new Post(user2, "post3", "This is user2 post", LocalDateTime.now(), null);
        postRepository.save(first);
        postRepository.save(second);
        postRepository.save(third);
        var posts = postRepository.findByAuthor(user);
        Assertions.assertThat(posts).hasSize(2);
        Assertions.assertThat(posts).extracting(Post::getText).contains("This is user1 post", "This is another user1 post");
    }

    @Test
    public void whenFindByCreatedAtBetween() {
        User user = new User("user@users.com", "User1", "password");
        userRepository.save(user);
        var first = new Post(user, "post1", "This is user1 post", LocalDateTime.now(), null);
        var second = new Post(user, "post2", "This is another user1 post", LocalDateTime.now(), null);
        var third = new Post(user, "post3", "This is latest post", LocalDateTime.now().plusDays(3), null);
        postRepository.save(first);
        postRepository.save(second);
        postRepository.save(third);
        var posts = postRepository.findByCreatedAtBetween(LocalDateTime.now().minusDays(1),
                LocalDateTime.now().plusDays(1));
        Assertions.assertThat(posts).hasSize(2);
        Assertions.assertThat(posts).extracting(Post::getText).contains("This is user1 post", "This is another user1 post");
    }

    @Test
    public void whenFindOrderByCreatedAt() {
        User user = new User("user@users.com", "User1", "password");
        userRepository.save(user);
        var first = new Post(user, "post1", "This should be in list",
                LocalDateTime.now().plusDays(1), null);
        var second = new Post(user, "post2", "This should not be in list",
                LocalDateTime.now().plusDays(3), null);
        var third = new Post(user, "post3", "This should be in list",
                LocalDateTime.now().plusDays(2), null);
        postRepository.save(first);
        postRepository.save(second);
        postRepository.save(third);
        var posts = postRepository.findByOrderByCreatedAt(PageRequest.of(0, 2));
        Assertions.assertThat(posts).hasSize(2);
        Assertions.assertThat(posts).contains(first, third);
    }

    @Test
    public void whenUpdatePost() {
        User user = new User("user@users.com", "User1", "password");
        userRepository.save(user);
        var myPost = new Post(user, "post1", "text1", LocalDateTime.now(), null);
        postRepository.save(myPost);
        int id = myPost.getId();
        postRepository.updatePost("updatedTitle", "updatedText", null, id);
        var found = postRepository.findById(id);
        Assertions.assertThat(found.get().getTitle()).isEqualTo("updatedTitle");
        Assertions.assertThat(found.get().getText()).isEqualTo("updatedText");
    }

    @Test
    public void whenPatchPost() {
        User user = new User("user@users.com", "User1", "password");
        userRepository.save(user);
        var myPost = new Post(user, "post1", "text1", LocalDateTime.now(), null);
        postRepository.save(myPost);
        int id = myPost.getId();
        var myPost2 = new Post(user, "updatedTitle", null, LocalDateTime.now(), null);
        myPost2.setId(myPost.getId());
        postRepository.patch(myPost2);
        var found = postRepository.findById(id);
        Assertions.assertThat(found.get().getTitle()).isEqualTo("updatedTitle");
        Assertions.assertThat(found.get().getText()).isEqualTo("text1");
    }

    @Test
    public void whenDeleteFileFromPost() {
        User user = new User("user@users.com", "User1", "password");
        userRepository.save(user);
        File file = new File("file1", "path1");
        fileRepository.save(file);
        var myPost = new Post(user, "post1", "text1", LocalDateTime.now(), file);
        postRepository.save(myPost);
        int id = myPost.getId();
        postRepository.deleteFileFromPost(id);
        assertThat(postRepository.findById(id).get().getFile()).isNull();
    }

    @Test
    public void whenDeletePostById() {
        User user = new User("user@users.com", "User1", "password");
        userRepository.save(user);
        var myPost = new Post(user, "post1", "text1", LocalDateTime.now(), null);
        postRepository.save(myPost);
        int id = myPost.getId();
        assertThat(postRepository.findById(id).isPresent());
        postRepository.deletePostById(id);
        Assertions.assertThat(postRepository.findById(id)).isEmpty();
    }

    @Test
    public void whenFindPostsOrderDescByFollowers() {
        User user1 = new User("user1@users.com", "User1", "password");
        User user2 = new User("user2@users.com", "User2", "password");
        User user3 = new User("user3@users.com", "User3", "password");
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        var user2post = new Post(user2, "post2", "text2", LocalDateTime.now(), null);
        var user3post = new Post(user3, "post3", "text3", LocalDateTime.now(), null);
        postRepository.save(user2post);
        postRepository.save(user3post);
        followerRepository.save(new Follower(user2, user1));
        followerRepository.save(new Follower(user3, user1));
        var posts = postRepository.
                findOrderDescByFollowers(PageRequest.of(0, 2), user1.getId());
        Assertions.assertThat(posts).contains(user2post, user3post);
    }
}