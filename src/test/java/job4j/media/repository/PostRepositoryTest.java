package job4j.media.repository;

import job4j.media.model.Post;
import job4j.media.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        var myPost = new Post(user, "post1", "This is my post");
        var hisPost = new Post(user, "post3", "This is his post");
        postRepository.save(myPost);
        postRepository.save(hisPost);
        var posts = postRepository.findAll();
        assertThat(posts).hasSize(2);
        assertThat(posts).extracting(Post::getText).contains("This is my post", "This is his post");
    }
}