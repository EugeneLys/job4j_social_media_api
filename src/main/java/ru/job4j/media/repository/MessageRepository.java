package ru.job4j.media.repository;

import ru.job4j.media.model.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Integer> {
}
