package ru.job4j.media.repository;

import ru.job4j.media.model.Message;
import ru.job4j.media.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MessageRepositoryTest {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        messageRepository.deleteAll();
    }

    @Test
    public void whenSaveThenFindById() {
        User user = new User("user@users.com", "User1", "password");
        User user2 = new User("user2@users.com", "User2", "password2");
        userRepository.save(user);
        userRepository.save(user2);
        var message = new Message();
        message.setSender(user);
        message.setAddressee(user2);
        message.setTitle("hello");
        message.setText("Good luck");
        messageRepository.save(message);
        var found = messageRepository.findById(message.getId());
        Assertions.assertThat(found).isPresent();
        Assertions.assertThat(found.get().getTitle()).isEqualTo("hello");
    }

    @Test
    public void whenFindAllThenReturnAll() {
        User user = new User("user@users.com", "User1", "password");
        User user2 = new User("user2@users.com", "User2", "password2");
        userRepository.save(user);
        userRepository.save(user2);
        var message = new Message();
        message.setSender(user);
        message.setAddressee(user2);
        message.setTitle("hello");
        message.setText("Good luck");
        var message2 = new Message();
        message2.setSender(user);
        message2.setAddressee(user2);
        message2.setTitle("hi");
        message2.setText("Regards");
        messageRepository.save(message);
        messageRepository.save(message2);
        var messages = messageRepository.findAll();
        Assertions.assertThat(messages).hasSize(2);
        Assertions.assertThat(messages).extracting(Message::getText).contains("Good luck", "Regards");
    }
}