package ru.job4j.media.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.Objects;

@Validated
@Entity
@Table(name = "posts")
@Schema(description = "Post Model Information")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Schema(description = "User, wha created post")
    private User author;
    @NotBlank
    @Schema(description = "Title of the post")
    private String title;
    @NotBlank
    @Schema(description = "Full text of this post")
    private String text;
    @Column(name = "createdAt")
    @Schema(description = "When this post was created")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "file_id")
    private File file;

    public Post(User author, String title, String text, LocalDateTime createdAt, File file) {
        this.author = author;
        this.title = title;
        this.text = text;
        this.createdAt = createdAt;
        this.file = file;
    }

    public Post() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User user) {
        this.author = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return id == post.id && Objects.equals(title, post.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}
