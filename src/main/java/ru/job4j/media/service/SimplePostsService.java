package ru.job4j.media.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.job4j.media.model.Post;
import ru.job4j.media.repository.PostRepository;
import ru.job4j.media.repository.UserRepository;

import java.util.Collections;
import java.util.List;

@Transactional
@Service
public class SimplePostsService implements PostsService {

private final PostRepository postRepository;
private final UserRepository userRepository;

    public SimplePostsService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Post> findByAuthorId(int id) {
        return userRepository.findById(id)
                .map(postRepository::findByAuthor).orElse(Collections.emptyList());
    }
}
