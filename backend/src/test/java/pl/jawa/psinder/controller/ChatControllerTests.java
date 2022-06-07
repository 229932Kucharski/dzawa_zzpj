package pl.jawa.psinder.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import pl.jawa.psinder.dto.ChatDto;
import pl.jawa.psinder.entity.Chat;
import pl.jawa.psinder.service.ChatService;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
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
    public void getChatsControllerTest() throws Exception {
        mockMvc.perform(
                        get("/chat/all")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()
                );

        mockMvc.perform(
                        get("/chat")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()
                );
    }

    @Test
    public void getChatsByConnectionIdControllerTest() throws Exception {
        MvcResult result = mockMvc.perform(
                        get("/chat")
                                .param("id", "2")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        StringBuilder stringBuilder = new StringBuilder("[");
        List<Chat> chatList = chatService.getAllChats().stream().filter(p -> p.getConnection().getId() == 2).collect(Collectors.toList());
        String output = "";

        for (int i = 0; i < chatList.size(); i++) {
            output = chatList.get(i).getDateCreated().toInstant().toString();
            output = output.substring(0, output.length() - 1);
            output += ".000+00:00";
            stringBuilder.append("{"
                    + "\"username\":\"" + chatList.get(i).getUser().getUsername() + "\","
                    + "\"text\":\"" + chatList.get(i).getText() + "\","
                    + "\"date\":\"" + output + "\""
                    + "}");
            if(chatList.size() - 1 != i) {
                stringBuilder.append(",");
            } else {
                stringBuilder.append("]");
            }
        }
        assertEquals(content, stringBuilder.toString());

        result = mockMvc.perform(
                        get("/chat")
                                .param("id", "9")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();
        content = result.getResponse().getContentAsString();
        assertEquals(content, "[]");
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
