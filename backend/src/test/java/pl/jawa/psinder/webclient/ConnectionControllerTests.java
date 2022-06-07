package pl.jawa.psinder.webclient;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;
import pl.jawa.psinder.dto.ConnectionDto;
import pl.jawa.psinder.entity.Connection;
import pl.jawa.psinder.service.ConnectionService;
import pl.jawa.psinder.service.PetService;
import pl.jawa.psinder.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ConnectionControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ConnectionService connectionService;

    @Autowired
    private PetService petService;

    @Autowired
    private UserService userService;

    @Test
    @Order(1)
    public void getConnectionsTest() throws Exception {
        mockMvc.perform(get("/connections").contentType(MediaType.APPLICATION_JSON))
                .andExpect((content().contentType(MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    public void getConnectionByID() throws Exception {
        long testID = connectionService.getConnections().get(0).getId();
        MvcResult result = mockMvc.perform(get("/connections").param("id", String.valueOf(testID)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Connection connection = objectMapper.readValue(result.getResponse().getContentAsString(), Connection.class);
        assertEquals(connectionService.getConnections().get(0).getId(), connection.getId());
    }

    @Test
    @Order(3)
    public void getConnectionsByOwnerAndWalkerIDs() throws Exception {
        long ownerTestID = connectionService.getConnections().get(0).getOwner().getId();
        long walkerTestID = connectionService.getConnections().get(0).getWalker().getId();
        MvcResult result = mockMvc.perform(get("/connections").param("ownerid", String.valueOf(ownerTestID)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode jsonNode = objectMapper.readTree(result.getResponse().getContentAsString());
        Connection connection = objectMapper.readValue(jsonNode.get(0).toString(), Connection.class);
        assertEquals(connectionService.getConnectionsByOwnerId(ownerTestID).get(0).getOwner().getId(), connection.getOwner().getId());

        result = mockMvc.perform(get("/connections").param("walkerid", String.valueOf(walkerTestID)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        jsonNode = objectMapper.readTree(result.getResponse().getContentAsString());
        connection = objectMapper.readValue(jsonNode.get(0).toString(), Connection.class);
        assertEquals(connectionService.getConnectionsByWalkerId(walkerTestID).get(0).getWalker().getId(), connection.getWalker().getId());
    }

    @Test
    @Order(4)
    public void getConnectionsByOwnerAndWalkerIDsWithKeywords() throws Exception {
        Connection testConnection = connectionService.getConnections().get(0);
        long ownerTestID = testConnection.getOwner().getId();
        long walkerTestID = testConnection.getWalker().getId();
        String testKeyword = testConnection.getPet().getName();

        MultiValueMap<String, String> params = new HttpHeaders();
        params.add("ownerid", String.valueOf(ownerTestID));
        params.add("keyword", testKeyword);
        MvcResult result = mockMvc.perform(get("/connections").params(params).contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode jsonNode = objectMapper.readTree(result.getResponse().getContentAsString());
        Connection connection = objectMapper.readValue(jsonNode.get(0).toString(), Connection.class);
        assertEquals(connectionService.getConnectionsByOwnerIdWithKeyWord(ownerTestID, testKeyword).get(0).getOwner().getId(), connection.getOwner().getId());
        assertEquals(connectionService.getConnectionsByOwnerIdWithKeyWord(ownerTestID, testKeyword).get(0).getPet().getName(), connection.getPet().getName());

        params = new HttpHeaders();
        params.add("walkerid", String.valueOf(walkerTestID));
        params.add("keyword", testKeyword);
        result = mockMvc.perform(get("/connections").params(params).contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        jsonNode = objectMapper.readTree(result.getResponse().getContentAsString());
        connection = objectMapper.readValue(jsonNode.get(0).toString(), Connection.class);
        assertEquals(connectionService.getConnectionsByWalkerIdWithKeyWord(walkerTestID, testKeyword).get(0).getWalker().getId(), connection.getWalker().getId());
        assertEquals(connectionService.getConnectionsByWalkerIdWithKeyWord(walkerTestID, testKeyword).get(0).getPet().getName(), connection.getPet().getName());
    }

    @Test
    @Order(5)
    public void createConnection() throws Exception {
        JSONObject body = new JSONObject();
        JSONObject pet = new JSONObject();
        pet.put("id", petService.getPets().get(0).getId());
        JSONObject owner = new JSONObject();
        owner.put("id", userService.getAllUsers().get(0).getId());
        JSONObject walker = new JSONObject();
        walker.put("id", userService.getAllUsers().get(0).getId());
        body.put("pet", pet);
        body.put("owner", owner);
        body.put("walker", walker);
        body.put("status", "waiting");


        MvcResult result = mockMvc.perform(post("/connections/create").contentType(MediaType.APPLICATION_JSON).content(body.toString()))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @Order(6)
    public void changeStatus() throws Exception {
        MultiValueMap<String, String> params = new HttpHeaders();
        params.add("connectionid", String.valueOf(connectionService.getConnections().get(connectionService.getConnections().size() - 1).getId()));
        params.add("status", "accepted");

        MvcResult result = mockMvc.perform(put("/connections/status-update").params(params).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Optional<Connection> connection = connectionService.getConnectionById(Long.valueOf(result.getResponse().getContentAsString()));
        assertEquals("accepted", connection.get().getStatus().toString());
    }

    @Test
    @Order(7)
    public void deleteConnection() throws Exception {
        MvcResult result = mockMvc.perform(delete("/connections/delete").param("id", String.valueOf(connectionService.getConnections().get(connectionService.getConnections().size() - 1).getId())).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("Connection deleted", result.getResponse().getContentAsString());
    }
//
//    @Test
//    public void getChatsByConnectionIdControllerTest() throws Exception {
//        MvcResult result = mockMvc.perform(
//                        get("/chat")
//                                .param("id", "2")
//                                .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(status().isOk())
//                .andReturn();
//
//        String content = result.getResponse().getContentAsString();
//        StringBuilder stringBuilder = new StringBuilder("[");
//        List<Chat> chatList = chatService.getAllChats().stream().filter(p -> p.getConnection().getId() == 2).collect(Collectors.toList());
//        String output = "";
//
//        for (int i = 0; i < chatList.size(); i++) {
//            output = chatList.get(i).getDateCreated().toInstant().toString();
//            output = output.substring(0, output.length() - 1);
//            output += ".000+00:00";
//            stringBuilder.append("{"
//                    + "\"username\":\"" + chatList.get(i).getUser().getUsername() + "\","
//                    + "\"text\":\"" + chatList.get(i).getText() + "\","
//                    + "\"date\":\"" + output + "\""
//                    + "}");
//            if(chatList.size() - 1 != i) {
//                stringBuilder.append(",");
//            } else {
//                stringBuilder.append("]");
//            }
//        }
//        assertEquals(content, stringBuilder.toString());
//
//        result = mockMvc.perform(
//                        get("/chat")
//                                .param("id", "9")
//                                .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(status().isOk())
//                .andReturn();
//        content = result.getResponse().getContentAsString();
//        assertEquals(content, "[]");
//    }
//
//    @Test
//    public void createChatControllerTest() throws Exception {
//        ChatDto chat = new ChatDto(2, 3, "test");
//        int amount = chatService.getAllChats().size();
//        mockMvc.perform(
//                        post("/chat/create")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(objectMapper.writeValueAsString(chat))
//                )
//                .andExpect(status().isOk()
//                );
//        assertEquals(chatService.getAllChats().size(), amount + 1);
//        assertEquals(chatService.getAllChats().get(chatService.getAllChats().size() - 1).getText(), chat.getText());
//
//    }

}
