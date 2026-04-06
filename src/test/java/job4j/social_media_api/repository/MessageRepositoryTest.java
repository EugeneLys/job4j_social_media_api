package job4j.social_media_api.repository;

import job4j.social_media_api.model.Message;
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

    @BeforeEach
    public void setUp() {
        messageRepository.deleteAll();
    }

    @Test
    public void whenSaveThenFindById() {
        var message = new Message();
        message.setSender_id(1);
        message.setAddressee_id(3);
        message.setTitle("hello");
        message.setText("Good luck");
        messageRepository.save(message);
        var found = messageRepository.findById(message.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("hello");
    }

    @Test
    public void whenFindAllThenReturnAll() {
        var message = new Message();
        message.setSender_id(1);
        message.setAddressee_id(3);
        message.setTitle("hello");
        message.setText("Good luck");
        var message2 = new Message();
        message2.setSender_id(7);
        message2.setAddressee_id(1);
        message2.setTitle("hi");
        message2.setText("Regards");
        messageRepository.save(message);
        messageRepository.save(message2);
        var messages = messageRepository.findAll();
        assertThat(messages).hasSize(2);
        assertThat(messages).extracting(Message::getText).contains("Good luck", "Regards");
    }
}