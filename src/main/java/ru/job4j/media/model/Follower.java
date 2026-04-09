package ru.job4j.media.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "followers")
public class Follower {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private User follower;
    @ManyToOne
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
