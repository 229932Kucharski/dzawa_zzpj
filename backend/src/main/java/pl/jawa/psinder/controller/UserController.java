package pl.jawa.psinder.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;
import pl.jawa.psinder.dto.UserDto;
import pl.jawa.psinder.dto.VerificationDto;
import pl.jawa.psinder.entity.User;
import pl.jawa.psinder.service.UserService;
import pl.jawa.psinder.webclient.MailSender;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final MailSender emailService;

    public UserController(UserService userService, MailSender emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    //get all users from repository
    @GetMapping("")
    public List<UserDto> getAllUsers() {
        List<User> allUsers = userService.getAllUsers();
        List<UserDto> allUsersSafeData = new ArrayList<>();
        for (User user : allUsers) {
            UserDto userDto = new UserDto(
                    user.getId(),
                    user.getUsername(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail());
            allUsersSafeData.add(userDto);
        }
        return allUsersSafeData;
    }
    //get one specific user using their username
    @GetMapping("/username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        Optional<User> user = userService.getUserByUsername(username);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        UserDto userDto = new UserDto(
                user.get().getId(),
                user.get().getUsername(),
                user.get().getFirstName(),
                user.get().getLastName(),
                user.get().getEmail());
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
                user.get().getId(),
                user.get().getUsername(),
                user.get().getFirstName(),
                user.get().getLastName(),
                user.get().getEmail());
        return ResponseEntity.ok(userDto);
    }
//    Commented out this code due to being replaced by save() from JwtAuthenticationController
//    @PostMapping("/add")
//    public User createUser(@RequestBody UserDto userDto, @RequestParam(name = "password", required = false)String password) {
//        return userService.addUser(new User(
//                userService.getCreationId(),
//                userDto.getFirstName(),
//                userDto.getLastName(),
//                userDto.getUsername(),
//                Objects.requireNonNullElse(password, "defaultPassword"),
//                userDto.getEmail()));
//    }
    //update existing user
    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUser(@RequestBody UserDto userDto, @PathVariable long id) throws ResourceAccessException {
        User user = userService.getUserById(id).orElseThrow(() -> new ResourceAccessException("User not found on :: "+ id));
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        final User updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(updatedUser);
    }
    //delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User with id " + id + " is removed.");
    }
    //get data to verify and sent verification mail
    @PostMapping("/{id}/verification")
    public ResponseEntity<String>  sendToVerify (@RequestBody VerificationDto verificationDto, @PathVariable long id) {
        User user = userService.getUserById(id).orElseThrow(() -> new ResourceAccessException("User not found on :: "+ id));

        emailService.sendVerificationMessage(
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                verificationDto.getRegon(),
                verificationDto.getAddress(), id
        );

        return ResponseEntity.ok("The application has been sent for verification");
    }
    //accept verification
    @PostMapping("/{id}/verified")
    public ResponseEntity<String> validateVerify (@PathVariable long id) {
        User user = userService.getUserById(id).orElseThrow(() -> new ResourceAccessException("User not found on :: "+ id));
        user.setVerified(true);
        final User updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(user.getUsername() + " user has been verified");
    }

}
