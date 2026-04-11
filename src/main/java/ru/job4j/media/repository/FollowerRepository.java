package ru.job4j.media.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.job4j.media.model.Follower;
import org.springframework.data.repository.CrudRepository;

public interface FollowerRepository extends CrudRepository<Follower, Integer> {

}
