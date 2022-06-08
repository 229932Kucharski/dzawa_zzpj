package pl.jawa.psinder.webclient;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.jawa.psinder.entity.User;
import pl.jawa.psinder.service.UserService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserService userService;

    @Test
    public void addAndDeleteUserTest() {
        User userA = new User();
        User userB = new User();
        userService.addUser(userA);
        userService.addUser(userB);

        List<User> users = userService.getAllUsers();

        assertThat(users).isNotNull();

        int userAmountBoth = userService.getAllUsers().size();

        userService.deleteUser(userB.getId());

        int userAmountOne = userService.getAllUsers().size();

        userService.deleteUser(userA.getId());

        assertThat(userAmountBoth).isNotEqualTo(userAmountOne);

    }
}
