package ru.job4j.media.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.job4j.media.dto.FileDto;
import ru.job4j.media.model.Post;
import ru.job4j.media.repository.PostRepository;

import java.util.Optional;

@Transactional
@Service
public class SimplePostService implements PostService {

    private final PostRepository postRepository;

    public SimplePostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post create(Post post, FileDto image) {
        return postRepository.save(post);
    }

    @Override
    public boolean update(Post post, FileDto image) {
        return postRepository.updatePost(post.getTitle(), post.getText(), post.getFile(), post.getId()) != 0;
    }

    @Override
    public boolean deleteById(int id) {
        return postRepository.deletePostById(id) != 0;
    }

    @Override
    public Optional<Post> findById(int id) {
        return postRepository.findById(id);
    }

    @Override
    public int patch(Post post) {
        return postRepository.patch(post);
    }
}
