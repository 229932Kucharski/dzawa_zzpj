package pl.jawa.psinder.webclient;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.jawa.psinder.entity.Chat;
import pl.jawa.psinder.entity.Connection;
import pl.jawa.psinder.entity.User;
import pl.jawa.psinder.enums.Status;
import pl.jawa.psinder.repository.ConnectionRepository;
import pl.jawa.psinder.service.ChatService;
import pl.jawa.psinder.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    private User u, u2;
    private Connection c, c2;

    private void setup() {
        u = new User( "G", "W", "Un", "$2a$10$Fn5ReBxSA7m5lsZEIQ/JyOmIKQYIm5iVUx3ZMYIspzOWSI88h7Noy","abc@gmail.com");
        u2 = new User( "P", "O", "Kn", "$2a$10$Fn5ReBxSA7m5lsZEIQ/JyOmIKQYIm5iVUx3ZMYIspzOWSI88h7Noy","def@gmail.com");
        userService.addUser(u);
        userService.addUser(u2);
        c = connectionRepository.getById(2L);
        c2 = connectionRepository.getById(3L);
    }

    private void clean() {
        userService.deleteUser(u.getId());
        userService.deleteUser(u2.getId());
    }


    @Test
    public void addAndDeleteChatTest() {

        setup();

        List<Chat> chats = chatService.getAllChats();
        assertThat(chats).isNotNull();
        int i = chats.size();


        Chat ch = new Chat(c, u2, "test");

        chatService.addChat(ch);
        assertEquals(chatService.getAllChats().size(), i + 1);

        chatService.deleteChat(ch.getId());
        assertEquals(chatService.getAllChats().size(), i);

        clean();
    }

    @Test
    public void getChatTests() {

        setup();

        Chat ch = new Chat(c, u2, "test");
        Chat ch2 = new Chat(c2, u, "test2");
        Chat ch3 = new Chat(c2, u2, "test3");

        chatService.addChat(ch);
        chatService.addChat(ch2);
        chatService.addChat(ch3);

        List<Chat> chats = chatService.getChatByConnectionId(3);
        List<Chat> chats1 = new ArrayList<>();
        chats1.add(ch2);
        chats1.add(ch3);
        assertEquals(chats.size(), chats1.size());

        chatService.deleteChat(ch.getId());
        chatService.deleteChat(ch2.getId());
        chatService.deleteChat(ch3.getId());

        clean();

    }

}
