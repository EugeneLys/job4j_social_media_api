package ru.job4j.media.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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

@Tag(name = "PostsController", description = "PostsController management APIs")
@Validated
@Slf4j
@RestController
@RequestMapping("/api/posts")
public class PostsController {

    private final PostsService postsService;

    public PostsController(PostsService postsService) {
        this.postsService = postsService;
    }

    @Operation(
            summary = "Get all posts of user.",
            description = "Get a collection of all posts of specific user by user's Id.",
            tags = { "Post", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Post.class),
                    mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/{userId}")
    public ResponseEntity<List<Post>> get(@PathVariable("userId")
                                              @NotNull
                                              @Min(value = 1, message = "User Id must be no less than 1") int id) {
        List<Post> posts = postsService.findByAuthorId(id);
        return ResponseEntity.ok(posts);
    }
}
