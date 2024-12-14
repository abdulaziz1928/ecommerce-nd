package com.example.demo.controllers;

import com.example.demo.infrastructure.common.exceptions.RegistrationException;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;


@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/id/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        return ResponseEntity.of(userRepository.findById(id));
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> findByUserName(@PathVariable String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        return optionalUser.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Transactional
    @PostMapping("/create")
    public ResponseEntity<User> createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        validateCreateUser(createUserRequest);
        User user = new User();
        user.setUsername(createUserRequest.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(createUserRequest.getPassword()));
        Cart cart = new Cart();
        cartRepository.save(cart);
        user.setCart(cart);
        userRepository.save(user);
        log.info("User (username: {}) has been created successfully", user.getUsername());
        return ResponseEntity.ok(user);
    }

    private void validateCreateUser(CreateUserRequest createUserRequest) {
        String username = createUserRequest.getUsername();
        userRepository.findByUsername(username)
                .ifPresent((user) -> {
                    throw new RegistrationException(String.format("user with username (%s) already exists", username));
                });

        String password = createUserRequest.getPassword();
        String confirmPassword = createUserRequest.getConfirmPassword();
        if (password.length() < 8) {
            throw new RegistrationException("password must be more than 8 characters");
        }
        if (!password.equals(confirmPassword)) {
            throw new RegistrationException("password, and confirm password must match");
        }
    }

}
