package pl.jawa.psinder.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class LoginController {

    @GetMapping("/user")
    public Principal user(Principal user) {
        return user;
    }

}
