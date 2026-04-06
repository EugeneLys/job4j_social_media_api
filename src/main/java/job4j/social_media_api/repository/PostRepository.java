package job4j.social_media_api.repository;

import job4j.social_media_api.model.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Integer> {
}
