package ru.job4j.media.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.job4j.media.repository.PostRepository;

@Component("securityService")
public class SecurityService {

    @Autowired
    private PostRepository postRepository;

    public boolean isAuthor(int postId, String name) {
        return postRepository.findById(postId)
                .map(post -> post.getAuthor().getName().equals(name))
                .orElse(false);
    }
}