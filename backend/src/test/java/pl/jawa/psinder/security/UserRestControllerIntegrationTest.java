package pl.jawa.psinder.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import pl.jawa.psinder.PsinderApplication;
import pl.jawa.psinder.controller.JwtAuthenticationController;
import pl.jawa.psinder.repository.UserRepository;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = PsinderApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:integrationtest.properties")
public class UserRestControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void should_return_users()
            throws Exception {

        mvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void should_return_userById()
            throws Exception {

        mvc.perform(get("/users/id/1")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.username", is("Krzysiek")))
                .andExpect(status().isOk());
    }

    @Test
    public void should_return_userByUsername()
            throws Exception {

        mvc.perform(get("/users/username/Krzysiek")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.username", is("Krzysiek")))
                .andExpect(status().isOk());
    }

    @Test
    public void should_update_user()
            throws Exception {

        mvc.perform(patch("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"Krzysiek\"," +
                                "\"firstName\": \"Drzysztow\"," +
                                "\"lastName\": \"Kwużnik\"," +
                                "\"email\": \"krzysiek.dwuznik@gmail.com\"," +
                                "\"verified\": \"false\"}")
                )
                .andExpect(status().isOk());
    }

    @Test
    public void should_not_authenticate() throws Exception {

        mvc.perform(post("/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"Krzysiek\"," +
                                "\"password\": \"notvalid\"}")
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void should_authenticate_user() throws Exception {

        mvc.perform(post("/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"Gelo\"," +
                                "\"password\": \"qwerty\"}")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("Gelo")))
                .andExpect(jsonPath("$.id", is(3)));
    }

    @Test
    public void should_register_user() throws Exception {

        mvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"Nowy\"," +
                                "\"firstName\": \"Nowy\"," +
                                "\"lastName\": \"Nowy\"," +
                                "\"email\": \"nowy.nowy@gmail.com\"," +
                                "\"password\": \"qwerty\"}")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(7)));
    }

    @Test
    public void should_not_register_user() throws Exception {

        mvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"Gelo\"," +
                                "\"firstName\": \"Nowy\"," +
                                "\"lastName\": \"Nowy\"," +
                                "\"email\": \"nowy.nowy@gmail.com\"," +
                                "\"password\": \"qwerty\"}")
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Username is already taken!"));
    }



}
