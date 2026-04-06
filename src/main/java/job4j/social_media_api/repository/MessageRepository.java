package job4j.social_media_api.repository;

import job4j.social_media_api.model.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Integer> {
}
