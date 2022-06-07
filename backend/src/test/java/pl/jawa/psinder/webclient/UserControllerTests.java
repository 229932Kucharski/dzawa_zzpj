package pl.jawa.psinder.webclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
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
import pl.jawa.psinder.entity.User;
import pl.jawa.psinder.service.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Test
    public void getAllUsersTest() throws Exception {
        mockMvc.perform(get("/users").contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getUserByIdTest() throws Exception {
        long userId = userService.getAllUsers().get(0).getId();
        MvcResult result = mockMvc.perform(get("/users/id/" + userId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        User user = objectMapper.readValue(result.getResponse().getContentAsString(), User.class);
        assertEquals(userService.getAllUsers().get(0).getId(), user.getId());
    }
    @Test
    public void getUserByUsernameTest() throws Exception {
        String username = userService.getAllUsers().get(0).getUsername();
        MvcResult result = mockMvc.perform(get("/users/username/" + username).contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        User user = objectMapper.readValue(result.getResponse().getContentAsString(), User.class);
        assertEquals(userService.getAllUsers().get(0).getUsername(), user.getUsername());
    }

    @Test
    public void patchAndDeleteUserTest() throws Exception {
        User user = new User(99999,
                "change-firstname",
                "change-lastname",
                "change-username",
                "default",
                "change-email@invalid.com",
                true);
        userService.addUser(user);

        long userId = userService.getUserByUsername("change-username").get().getId();

        JSONObject body = new JSONObject();
        body.put("username", "test-username");
        body.put("firstName", "test-firstname");
        body.put("lastName", "test-lastname");
        body.put("email", "test-email@invalid.com");
        MvcResult patch_result = mockMvc.perform(patch("/users/" + userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body.toString()))
                .andExpect(status().isOk())
                .andReturn();
        MvcResult delete_result = mockMvc.perform(delete("/users/" + userId))
                .andExpect(status().isOk())
                .andReturn();

    }
}
