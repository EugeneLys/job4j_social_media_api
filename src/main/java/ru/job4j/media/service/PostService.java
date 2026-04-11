package ru.job4j.media.service;

import ru.job4j.media.dto.FileDto;
import ru.job4j.media.model.Post;

public interface PostService {

    Post create(Post post, FileDto image);
    boolean update(Post post, FileDto image);
    boolean deleteById(int id);

}
