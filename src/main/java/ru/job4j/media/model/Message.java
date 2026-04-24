package ru.job4j.media.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

@Validated
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Positive
    private int id;
    @ManyToOne
    private User sender;
    @ManyToOne
    private User addressee;
    @NotBlank
    private String title;
    @NotBlank
    private String text;

    public Message(User sender, User addressee, String title, String text) {
        this.sender = sender;
        this.addressee = addressee;
        this.title = title;
        this.text = text;
    }

    public Message() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getAddressee() {
        return addressee;
    }

    public void setAddressee(User addressee) {
        this.addressee = addressee;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Message message = (Message) o;
        return id == message.id && Objects.equals(title, message.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sender, addressee, title);
    }
}
