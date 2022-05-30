package pl.jawa.psinder.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jawa.psinder.dto.UserDto;
import pl.jawa.psinder.entity.User;
import pl.jawa.psinder.repository.UserRepository;
import pl.jawa.psinder.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //get all users from repository
    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    //get one specific user
    @GetMapping("")
    public ResponseEntity<?> getUserByUsername(@RequestParam("username")String username) {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        UserDto userDto = new UserDto(
                user.getUsername(), user.getFirstName(), user.getLastName(), user.getEmail()
        );
        return ResponseEntity.ok(userDto);
    }


}
