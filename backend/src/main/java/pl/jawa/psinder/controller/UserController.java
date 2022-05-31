package pl.jawa.psinder.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jawa.psinder.dto.UserDto;
import pl.jawa.psinder.entity.User;
import pl.jawa.psinder.repository.UserRepository;
import pl.jawa.psinder.service.UserService;

import java.util.List;
import java.util.Optional;

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
    //get one specific user using their username
    @GetMapping("/name/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        Optional<User> user = userService.getUserByUsername(username);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        UserDto userDto = new UserDto(
                user.get().getUsername(), user.get().getFirstName(), user.get().getLastName(), user.get().getEmail()
        );
        return ResponseEntity.ok(userDto);
    }
    //get one specific user using their ID
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getUserById(@PathVariable long id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        UserDto userDto = new UserDto(
                user.get().getUsername(), user.get().getFirstName(), user.get().getLastName(), user.get().getEmail()
        );
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/add")
    public User createUser(@RequestBody UserDto userDto, @RequestParam(name = "password", required = false)String password) {
        if(password == null) {
            return userService.addUser(new User(
                    userService.getCreationId(),
                    userDto.getFirstName(),
                    userDto.getLastName(),
                    userDto.getUsername(),
                    "password",
                    userDto.getEmail()));
        }
        return  userService.addUser(new User(
                userService.getCreationId(),
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getUsername(),
                password,
                userDto.getEmail()));
    }

}
