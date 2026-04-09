package ru.job4j.media.repository;

import ru.job4j.media.model.Follower;
import org.springframework.data.repository.CrudRepository;

public interface FollowerRepository extends CrudRepository<Follower, Integer> {
}
