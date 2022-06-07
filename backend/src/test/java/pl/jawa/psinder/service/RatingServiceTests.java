package pl.jawa.psinder.service;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.boot.test.context.SpringBootTest;
import pl.jawa.psinder.entity.Rating;
import pl.jawa.psinder.entity.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RatingServiceTests {

    @Autowired
    private RatingService ratingService;
    @Autowired
    private UserService userService;

    private static Rating rate1, rate2;
    private static User user1, user2;

    @Test
    public void getRatingTests() {
        int rateSize = ratingService.getAllRates().size();
        create();
        ratingService.addRate(rate1);
        ratingService.addRate(rate2);
        assertEquals(rateSize + 2, ratingService.getAllRates().size());
        assertEquals(rate1, ratingService.getRateById(rate1.getId()));
        assertEquals(1, ratingService.getByUserId(user1.getId(), true).size());
        assertEquals(1, ratingService.getByUserId(user1.getId(), false).size());
        clean();
    }

    @Test
    public void getSortRatingTests() {
        create();
        ratingService.addRate(rate1);
        ratingService.addRate(rate2);
        int borderRate = 5;
        int rateSize = ratingService.getAllRates().size();
        List<Rating> sortRate = ratingService.getByRates(borderRate, true);
        assertTrue(sortRate.size() < rateSize);
        assertNotEquals(sortRate, ratingService.getByRates(borderRate, false));
        clean();
    }

    @Test
    public void ratingOperationsTests() {
        create();
        ratingService.addRate(rate1);
        assertThat(ratingService.getRateById(rate2.getId())).isNull();
        ratingService.addRate(rate2);
        assertThat(ratingService.getRateById(rate2.getId())).isNotNull();

        String lastComment = rate1.getComment();
        String newComment = "123 Proba mikrofonu";
        rate1.setComment(newComment);
        ratingService.updateRate(rate1);
        assertNotEquals(lastComment, ratingService.getRateById(rate1.getId()).getComment());
        assertEquals(newComment, ratingService.getRateById(rate1.getId()).getComment());

        ratingService.deleteRate(rate2.getId());
        assertThat(ratingService.getRateById(rate2.getId())).isNull();
        rate2 = new Rating(7, "test", user2, user1);
        ratingService.addRate(rate2);
        clean();
    }

    private void create() {
        user1 = new User("G", "W", "Un", "$2a$10$Fn5ReBxSA7m5lsZEIQ/JyOmIKQYIm5iVUx3ZMYIspzOWSI88h7Noy","abc@gmail.com");
        user2 = new User("P", "O", "Kn", "$2a$10$Fn5ReBxSA7m5lsZEIQ/JyOmIKQYIm5iVUx3ZMYIspzOWSI88h7Noy","def@gmail.com");
        userService.addUser(user1);
        userService.addUser(user2);
        rate1 = new Rating(3, "tmp", user1, user2);
        rate2 = new Rating(7, "test", user2, user1);
    }

    private void clean() {
        ratingService.deleteRate(rate1.getId());
        ratingService.deleteRate(rate2.getId());
        userService.deleteUser(user1.getId());
        userService.deleteUser(user2.getId());
    }
}
