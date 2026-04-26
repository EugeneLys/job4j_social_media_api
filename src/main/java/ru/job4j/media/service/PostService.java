package ru.job4j.media.service;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import ru.job4j.media.dto.FileDto;
import ru.job4j.media.model.Post;

import java.util.Optional;

@Validated
public interface PostService {

    Post create(@Valid Post post, @Valid FileDto image);

    boolean update(@Valid Post post, @Valid FileDto image);

    boolean deleteById(int id);

    Optional<Post> findById(int id);

    int patch(Post post);
}
