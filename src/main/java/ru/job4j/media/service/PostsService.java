package ru.job4j.media.service;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import ru.job4j.media.model.Post;

import java.util.List;

@Validated
public interface PostsService {

   List<Post> findByAuthorId(@Valid int id);
}
