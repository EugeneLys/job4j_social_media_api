package ru.job4j.media.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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

@Tag(name = "PostController", description = "PostController management APIs")
@Validated
@Slf4j
@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Operation(
            summary = "Retrieve a post by Id",
            description = "Get a Post object by specifying its Id. "
                    + "The response is Post object with author (user), title, text and time of creation.",
            tags = { "Post", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Post.class),
                    mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/{id}")
    public ResponseEntity<Post> get(@PathVariable("id")
                                        @NotNull
                                        @Min(value = 1, message = "Post Id must be no less than 1") int id) {
        return postService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Save new post",
            description = "Save new post with attached file or without.",
            tags = { "Post", "save" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Post.class),
                    mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }) })
    @PostMapping
    public ResponseEntity<Post> save(@Valid @RequestPart("post") Post post,
                                     @Valid @RequestPart("file") MultipartFile file) throws IOException {
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

    @Operation(
            summary = "Update a post",
            description = "Replace a post by new one.",
            tags = { "Post", "update" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Post.class),
                    mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }) })
    @PutMapping
    public ResponseEntity<Void> update(@Valid @RequestPart("post") Post post,
                                       @Valid @RequestPart("file") MultipartFile file) throws IOException {
        FileDto fileDto = new FileDto(file.getOriginalFilename(), file.getBytes());
        if (postService.update(post, fileDto)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Change post",
            description = "Change something in this post - title or text or something else.",
            tags = { "Post", "change" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Post.class),
                    mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }) })
    @PatchMapping
    public ResponseEntity<Void> change(@Valid @RequestPart("post") Post post,
                                       @Valid @RequestPart("file") MultipartFile file) throws IOException {
        FileDto fileDto = new FileDto(file.getOriginalFilename(), file.getBytes());
        if (postService.patch(post) > 0) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Remove post",
            description = "Remove post from base by Id.",
            tags = { "Post", "remove" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Post.class),
                    mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }) })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeById(@PathVariable int id) {
        if (postService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
