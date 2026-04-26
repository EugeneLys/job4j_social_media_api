package ru.job4j.media.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

@Validated
@Entity
@Schema(description = "Message Model Information")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @Schema(description = "User who sent")
    private User sender;
    @ManyToOne
    @Schema(description = "To whom it was sent")
    private User addressee;
    @NotBlank
    @Schema(description = "Title of the message")
    private String title;
    @NotBlank
    @Schema(description = "Message itself")
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
