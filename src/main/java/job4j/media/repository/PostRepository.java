package job4j.media.repository;

import job4j.media.model.Post;
import job4j.media.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends CrudRepository<Post, Integer> {

    List<Post> findByAuthor(User author);

    List<Post>findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    Page<Post> findByOrderByCreatedAt(Pageable pageable);
}
