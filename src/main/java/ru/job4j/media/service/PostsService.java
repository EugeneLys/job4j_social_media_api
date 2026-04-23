package ru.job4j.media.service;

import ru.job4j.media.model.Post;

import java.util.List;

public interface PostsService {

   List<Post> findByAuthorId(int id);
}
