package pl.jawa.psinder.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.jawa.psinder.dto.UserDto;
import pl.jawa.psinder.entity.User;
import pl.jawa.psinder.repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("")
    public ResponseEntity<?> getUserByUsername(@RequestParam("username")String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        UserDto userDto = new UserDto(
                user.getUsername(), user.getFirstName(), user.getLastName(), user.getEmail()
        );
        return ResponseEntity.ok(userDto);
    }


}
