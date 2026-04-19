package ru.job4j.media.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.job4j.media.dto.FileDto;
import ru.job4j.media.model.Post;
import ru.job4j.media.service.PostService;

import java.io.IOException;

@Validated
@Slf4j
@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> get(@PathVariable("id") int id) {
        return postService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Post> save(@RequestPart("post") Post post,
                                     @RequestPart("file") MultipartFile file) throws IOException {
        FileDto fileDto = new FileDto(file.getOriginalFilename(), file.getBytes());
        postService.create(post, fileDto);
        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(uri)
                .body(post);
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestPart("post") Post post,
                                       @RequestPart("file") MultipartFile file) throws IOException {
        FileDto fileDto = new FileDto(file.getOriginalFilename(), file.getBytes());
        if (postService.update(post, fileDto)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping
    public ResponseEntity<Void> change(@RequestPart("post") Post post,
                                       @RequestPart("file") MultipartFile file) throws IOException {
        FileDto fileDto = new FileDto(file.getOriginalFilename(), file.getBytes());
        if (postService.patch(post) > 0) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeById(@PathVariable int id) {
        if (postService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
