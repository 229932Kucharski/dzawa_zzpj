package pl.jawa.psinder.webclient;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.internal.matchers.Matches;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import pl.jawa.psinder.dto.ChatDto;
import pl.jawa.psinder.dto.UserDto;
import pl.jawa.psinder.entity.User;
import pl.jawa.psinder.service.ChatService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ChatControllerTests {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ChatService chatService;

    @Test
    public void getAllChatsControllerTest() throws Exception {
        mockMvc.perform(
                    get("/chat/all")
                    .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()
        );
    }

    @Test
    public void createChatControllerTest() throws Exception {
        ChatDto chat = new ChatDto(2, 3, "test");
        int amount = chatService.getAllChats().size();
        mockMvc.perform(
                    post("/chat/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(chat))
                )
                .andExpect(status().isOk()
        );
        assertEquals(chatService.getAllChats().size(), amount + 1);
        assertEquals(chatService.getAllChats().get(chatService.getAllChats().size() - 1).getText(), chat.getText());

    }

}
