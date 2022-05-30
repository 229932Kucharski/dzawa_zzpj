package pl.jawa.psinder.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jawa.psinder.entity.User;
import pl.jawa.psinder.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public Optional<User> getUserById(long id) {
        return userRepository.findById(id);
    }
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
