package ru.job4j.media.dto;

import ru.job4j.media.model.Post;

import java.util.Collection;

public class PostsDto {

    private int userId;
    private String name;
    Collection<Post> posts;

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

    public Collection<Post> getPosts() {
        return posts;
    }

    public void setPosts(Collection<Post> posts) {
        this.posts = posts;
    }
}
