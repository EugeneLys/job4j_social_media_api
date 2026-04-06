package job4j.social_media_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int sender_id;
    private int addressee_id;
    private String title;
    private String text;

    public Message(int sender_id, int addressee_id, String title, String text) {
        this.sender_id = sender_id;
        this.addressee_id = addressee_id;
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

    public int getSender_id() {
        return sender_id;
    }

    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }

    public int getAddressee_id() {
        return addressee_id;
    }

    public void setAddressee_id(int addressee_id) {
        this.addressee_id = addressee_id;
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return sender_id == message.sender_id && addressee_id == message.addressee_id && Objects.equals(title, message.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sender_id, addressee_id, title);
    }
}
