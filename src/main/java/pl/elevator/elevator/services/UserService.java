package pl.elevator.elevator.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.elevator.elevator.models.User;
import pl.elevator.elevator.repositories.UserRepository;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService implements UserDetailsService {

    @Value("${Algorithm-key}")
    private String key;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public UserDetails accountVerify(String username, String password) {
        UserDetails userDetails = loadUserByUsername(username);
        if (passwordEncoder.matches(password, userDetails.getPassword()) && userDetails.isEnabled()) return userDetails;
        return null;
    }

    public ResponseEntity UserRegister(String username, String password) {
        if (userRepository.findFirstByUsername(username).isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        User userApp = new User();
        userApp.setUsername(username);
        userApp.setPassword(passwordEncoder.encode(password));
        if (userRepository.save(userApp) == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Map<String, String>> login(String username, String password) {
        try {
            UserDetails userDetails = loadUserByUsername(username);
            Map<String, String> user = new HashMap<>();
            if (userDetails == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            if (passwordEncoder.matches(password, userDetails.getPassword()) && userDetails.isEnabled()) {
                user.put("Username",userDetails.getUsername());
                user.put("Token", generateJwt(username, password));
                return new ResponseEntity<>(user,HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (UsernameNotFoundException e){
            System.err.println("user not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }



    }

    private String generateJwt(String username, String password) {
        Algorithm algorithm = Algorithm.HMAC512(key);
        return JWT.create().withClaim("username", username).withClaim("password", password).sign(algorithm);
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findAllByUsername(s);
    }


}
