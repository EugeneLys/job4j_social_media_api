package job4j.media.repository;

import job4j.media.model.Post;
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

    @BeforeEach
    public void setUp() {
        postRepository.deleteAll();
    }

    @Test
    public void whenSaveThenFindById() {
        var myPost = new Post();
        myPost.setUserId(1);
        myPost.setTitle("post1");
        myPost.setText("This is my post");
        postRepository.save(myPost);
        var found = postRepository.findById(myPost.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("post1");
    }

    @Test
    public void whenFindAllThenReturnAll() {
        var myPost = new Post(1, "post1", "This is my post");
        var hisPost = new Post(3, "post3", "This is his post");
        postRepository.save(myPost);
        postRepository.save(hisPost);
        var posts = postRepository.findAll();
        assertThat(posts).hasSize(2);
        assertThat(posts).extracting(Post::getText).contains("This is my post", "This is his post");
    }
}