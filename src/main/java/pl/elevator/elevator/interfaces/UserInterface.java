package pl.elevator.elevator.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface UserInterface {
    UserDetails accountVerify(String username, String password);
    ResponseEntity UserRegister(String username, String password);
    ResponseEntity<Map<String, String>> login(String username, String password);
}
