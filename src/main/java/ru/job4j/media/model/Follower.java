package ru.job4j.media.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

@Validated
@Entity
@Table(name = "followers")
@Schema(description = "Follower Model Information")
public class Follower {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @Schema(description = "This user is follower")
    @NotNull
    private User follower;
    @ManyToOne
    @Schema(description = "This user is followed by follower")
    @NotNull
    private User followed;

    public Follower(User follower, User followed) {
        this.follower = follower;
        this.followed = followed;
    }

    public Follower() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getFollower() {
        return follower;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }

    public User getFollowed() {
        return followed;
    }

    public void setFollowed(User followed) {
        this.followed = followed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Follower follower1 = (Follower) o;
        return id == follower1.id && Objects.equals(follower, follower1.follower)
                && Objects.equals(followed, follower1.followed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, follower, followed);
    }
}
