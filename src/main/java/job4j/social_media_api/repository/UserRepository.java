package job4j.social_media_api.repository;

import job4j.social_media_api.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
