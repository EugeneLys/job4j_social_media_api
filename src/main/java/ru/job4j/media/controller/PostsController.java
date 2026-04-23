package ru.job4j.media.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.media.model.Post;
import ru.job4j.media.service.PostsService;

import java.util.List;

@Validated
@Slf4j
@RestController
@RequestMapping("/api/posts")
public class PostsController {

    private final PostsService postsService;

    public PostsController(PostsService postsService) {
        this.postsService = postsService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Post>> get(@PathVariable("userId") int id) {
        List<Post> posts = postsService.findByAuthorId(id);
        return ResponseEntity.ok(posts);
    }
}
