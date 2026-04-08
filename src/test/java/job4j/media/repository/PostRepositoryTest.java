package job4j.media.repository;

import job4j.media.model.Post;
import job4j.media.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        postRepository.deleteAll();
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
        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("post1");
    }

    @Test
    public void whenFindAllThenReturnAll() {
        User user = new User("user@users.com", "User1", "password");
        userRepository.save(user);
        var myPost = new Post(user, "post1", "This is my post", LocalDateTime.now());
        var hisPost = new Post(user, "post3", "This is his post", LocalDateTime.now());
        postRepository.save(myPost);
        postRepository.save(hisPost);
        var posts = postRepository.findAll();
        assertThat(posts).hasSize(2);
        assertThat(posts).extracting(Post::getText).contains("This is my post", "This is his post");
    }

    @Test
    public void whenFindByAuthor() {
        User user = new User("user@users.com", "User1", "password");
        User user2 = new User("user2@users.com", "User2", "password2");
        userRepository.save(user);
        userRepository.save(user2);
        var first = new Post(user, "post1", "This is user1 post", LocalDateTime.now());
        var second = new Post(user, "post2", "This is another user1 post", LocalDateTime.now());
        var third = new Post(user2, "post3", "This is user2 post", LocalDateTime.now());
        postRepository.save(first);
        postRepository.save(second);
        postRepository.save(third);
        var posts = postRepository.findByAuthor(user);
        assertThat(posts).hasSize(2);
        assertThat(posts).extracting(Post::getText).contains("This is user1 post", "This is another user1 post");
    }

    @Test
    public void whenFindByCreatedAtBetween() {
        User user = new User("user@users.com", "User1", "password");
        userRepository.save(user);
        var first = new Post(user, "post1", "This is user1 post", LocalDateTime.now());
        var second = new Post(user, "post2", "This is another user1 post", LocalDateTime.now());
        var third = new Post(user, "post3", "This is latest post", LocalDateTime.now().plusDays(3));
        postRepository.save(first);
        postRepository.save(second);
        postRepository.save(third);
        var posts = postRepository.findByCreatedAtBetween(LocalDateTime.now().minusDays(1),
                LocalDateTime.now().plusDays(1));
        assertThat(posts).hasSize(2);
        assertThat(posts).extracting(Post::getText).contains("This is user1 post", "This is another user1 post");
    }

    @Test
    public void whenFindOrderByCreatedAt() {
        User user = new User("user@users.com", "User1", "password");
        userRepository.save(user);
        var first = new Post(user, "post1", "This should be in list", LocalDateTime.now().plusDays(1));
        var second = new Post(user, "post2", "This should not be in list", LocalDateTime.now().plusDays(3));
        var third = new Post(user, "post3", "This should be in list", LocalDateTime.now().plusDays(2));
        postRepository.save(first);
        postRepository.save(second);
        postRepository.save(third);
        var posts = postRepository.findByOrderByCreatedAt(PageRequest.of(0, 2));
        assertThat(posts).hasSize(2);
        assertThat(posts).contains(first, third);
    }
}