package ru.job4j.media.dto;

import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import ru.job4j.media.model.Post;

import java.util.List;

@Validated
public class PostsDto {

    @Positive
    private int userId;
    @Length(min = 2,
            message = "username must be from 2 letters length")
    private String name;
    List<Post> posts;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
