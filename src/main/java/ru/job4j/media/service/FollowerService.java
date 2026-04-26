package ru.job4j.media.service;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import ru.job4j.media.model.Follower;

@Validated
public interface FollowerService {

    void delete(@Valid Follower follower);

    boolean follow(@Valid Follower follower);

    int accept(@Valid Follower follower);
}
