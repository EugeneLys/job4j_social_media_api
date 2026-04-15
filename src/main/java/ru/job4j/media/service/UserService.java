package ru.job4j.media.service;

import ru.job4j.media.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User save(User user);

    int update(User user);

    int patch(User user);

    Optional<User> findById(int id);

    boolean deleteById(int id);

    List<User> findAll();
}
