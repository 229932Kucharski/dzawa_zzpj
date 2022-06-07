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
import pl.jawa.psinder.dto.RatingDto;
import pl.jawa.psinder.entity.Rating;
import pl.jawa.psinder.entity.User;
import pl.jawa.psinder.service.RatingService;
import pl.jawa.psinder.service.UserService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RatingControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper;

    private static User user1, user2;
    private static Rating rate;

    @Test
    public void getRatingControllerTest() throws Exception {
        create();
        MvcResult result = mockMvc.perform(
                        get("/rating")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        if (ratingService.getAllRates().size() == 0) {
            assertEquals("[]", result.getResponse().getContentAsString());
        } else {
            assertNotEquals("[]", result.getResponse().getContentAsString());
        }

        result = mockMvc.perform(
                        get("/rating/0/" + user1.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals("[]", result.getResponse().getContentAsString());

        result = mockMvc.perform(
                        get("/rating/1/" + user1.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        assertNotEquals("[]", result.getResponse().getContentAsString());
        clean();
    }

    @Test
    public void sortRatingTest() throws Exception {
        create();
        int borderRate = 5;
        MvcResult result1 = mockMvc.perform(
                        get("/rating/" + borderRate + "sort" + 1)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        MvcResult result2 = mockMvc.perform(
                        get("/rating/" + borderRate + "sort" + 0)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        assertNotEquals(result1, result2);

        result1 = mockMvc.perform(
                        get("/rating/" + 1 + "/" + user1.getId() + "/" + borderRate + "sort" + 0)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        assertNotEquals(result1, result2);
        result2 = mockMvc.perform(
                        get("/rating/" + 1 + "/" + user1.getId() + "/" + borderRate + "sort" + 1)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        assertNotEquals(result1, result2);
        clean();
    }

    @Test
    public void operationsRatingTest() throws Exception {
        create();
        Rating tmpRate = new Rating(0, "test", user2, user1);

        ratingService.addRate(tmpRate);
        int lastSize = ratingService.getAllRates().size();
        mockMvc.perform(
                        delete("/rating/delete" + tmpRate.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
        assertEquals(lastSize - 1, ratingService.getAllRates().size());
        clean();
    }

    @Test
    public void createRatingTest() throws Exception {

        create();

        RatingDto rating = new RatingDto(2, "Ladne", user1.getId(), user2.getId());
        int amount = ratingService.getAllRates().size();
        mockMvc.perform(
                        post("/rating/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(rating))
                )
                .andExpect(status().isOk()
                );
        assertEquals(ratingService.getAllRates().size(), amount + 1);
        assertEquals(ratingService.getAllRates().get(ratingService.getAllRates().size() - 1).getComment(), rating.getComment());
        assertEquals(ratingService.getAllRates().get(ratingService.getAllRates().size() - 1).getFromUser(), user1);
        assertEquals(ratingService.getAllRates().get(ratingService.getAllRates().size() - 1).getToUser(), user2);

        ratingService.deleteRate(ratingService.getAllRates().get(ratingService.getAllRates().size() - 1).getId());
        clean();
    }

    @Test
    public void updateRatingTest() throws Exception {

        create();

        Rating tmpRate = new Rating(0, "test", user2, user1);
        ratingService.addRate(tmpRate);

        System.out.println(tmpRate.getId());
        System.out.println(ratingService.getRateById(tmpRate.getId()));

        RatingDto rating = new RatingDto(
                2,
                tmpRate.getComment(),
                tmpRate.getFromUser().getId(),
                tmpRate.getToUser().getId()
        );
        mockMvc.perform(
                        post("/rating/update" + tmpRate.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(rating))
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()
                );
        assertEquals(ratingService.getRateById(tmpRate.getId()).getRate(), rating.getRating());
        assertNotEquals(ratingService.getRateById(tmpRate.getId()).getRate(), tmpRate.getRate());

        ratingService.deleteRate(tmpRate.getId());
        clean();
    }

    private void create() {
        user1 = new User("G", "W", "Un", "$2a$10$Fn5ReBxSA7m5lsZEIQ/JyOmIKQYIm5iVUx3ZMYIspzOWSI88h7Noy", "abc@gmail.com");
        user2 = new User("P", "O", "Kn", "$2a$10$Fn5ReBxSA7m5lsZEIQ/JyOmIKQYIm5iVUx3ZMYIspzOWSI88h7Noy", "def@gmail.com");
        userService.addUser(user1);
        userService.addUser(user2);
        rate = new Rating(5, "test", user1, user2);
        ratingService.addRate(rate);
    }

    private void clean() {
        ratingService.deleteRate(rate.getId());
        userService.deleteUser(user1.getId());
        userService.deleteUser(user2.getId());
    }

}
