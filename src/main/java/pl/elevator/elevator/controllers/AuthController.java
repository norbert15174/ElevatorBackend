package pl.elevator.elevator.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.elevator.elevator.services.UserService;


import java.util.Map;

@RestController
@RequestMapping(path = "/auth")
@CrossOrigin
public class AuthController {

    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity accountRegister(@RequestBody Map<String, String> register) {
        String username = register.get("username");
        String password = register.get("password");
        if (username.isBlank() || password.isBlank())
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        return userService.UserRegister(username, password);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> login) {
        String username = login.get("username");
        String password = login.get("password");
        if (username.isBlank() || password.isBlank()) return new ResponseEntity(HttpStatus.BAD_REQUEST);
        return userService.login(username, password);
    }


}
