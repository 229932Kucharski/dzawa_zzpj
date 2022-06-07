package pl.jawa.psinder.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.jawa.psinder.dto.VerificationDto;
import pl.jawa.psinder.entity.User;
import pl.jawa.psinder.service.UserService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class VerificationFromUserTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper;

    private static User user;

    @Test
    public void sendToVerifyTest() throws Exception{
        create();
        VerificationDto dataForVerify = new VerificationDto(598746597, "Tory Bucholskie");
        MvcResult result = mockMvc.perform(
                        post("/users/" + user.getId() + "/verification")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dataForVerify))
                )
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(
                "The application has been sent for verification",
                result.getResponse().getContentAsString());
        clean();
    }

    @Test
    public void validateVerify() throws Exception{
        create();
        assertFalse(user.isVerified());
        MvcResult result = mockMvc.perform(
                        post("/users/" + user.getId() + "/verified")
                                .contentType(MediaType.ALL)
                )
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(userService.getUserById(user.getId()).get().isVerified());
        assertEquals(
                userService.getUserById(user.getId()).get().getUsername() + " user has been verified",
                result.getResponse().getContentAsString());
        clean();
    }

    private void create() {
        user = new User("G", "W", "Un", "$2a$10$Fn5ReBxSA7m5lsZEIQ/JyOmIKQYIm5iVUx3ZMYIspzOWSI88h7Noy","abc@gmail.com");
        userService.addUser(user);
    }

    private void clean() {
        userService.deleteUser(user.getId());
    }
}
