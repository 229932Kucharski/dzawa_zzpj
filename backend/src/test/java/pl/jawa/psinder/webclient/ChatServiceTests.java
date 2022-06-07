package pl.jawa.psinder.webclient;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.jawa.psinder.entity.Chat;
import pl.jawa.psinder.entity.Connection;
import pl.jawa.psinder.entity.User;
import pl.jawa.psinder.enums.Status;
import pl.jawa.psinder.repository.ConnectionRepository;
import pl.jawa.psinder.service.ChatService;
import pl.jawa.psinder.service.UserService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ChatServiceTests {

    @Autowired
    private ChatService chatService;
    @Autowired
    private UserService userService;
    @Autowired
    private ConnectionRepository connectionRepository;

    private static User u, u2;
    private static Connection c;

    @BeforeAll
    private static void setup() {

        u = new User( "G", "W", "Un", "$2a$10$Fn5ReBxSA7m5lsZEIQ/JyOmIKQYIm5iVUx3ZMYIspzOWSI88h7Noy","abc@gmail.com");
        u2 = new User( "P", "O", "Kn", "$2a$10$Fn5ReBxSA7m5lsZEIQ/JyOmIKQYIm5iVUx3ZMYIspzOWSI88h7Noy","def@gmail.com");

    }


    @Test
    public void addAndDeleteChatTest() {
        userService.addUser(u);
        userService.addUser(u2);

        List<Chat> chats = chatService.getAllChats();
        assertThat(chats).isNotNull();
        int i = chats.size();

        c = connectionRepository.getById(2L);
        Chat ch = new Chat(c, u2, "test");

        chatService.addChat(ch);
        assertEquals(chatService.getAllChats().size(), i + 1);

        chatService.deleteChat(ch.getId());
        assertEquals(chatService.getAllChats().size(), i);

        userService.deleteUser(u.getId());
        userService.deleteUser(u2.getId());

    }
}
