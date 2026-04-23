package ru.job4j.media.dto;

import ru.job4j.media.model.Post;

import java.util.Collection;
import java.util.List;

public class PostsDto {

    private int userId;
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
