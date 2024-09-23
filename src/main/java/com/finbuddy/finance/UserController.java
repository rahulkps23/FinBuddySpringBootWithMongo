package com.finbuddy.finance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserCredentials>> getUserCredentials() {
        return new ResponseEntity<List<UserCredentials>>(userService.findAllUserCredentials(), HttpStatus.OK);
    }

    @PostMapping("/add-user")
    public ResponseEntity<Boolean> addUserCredentials(@RequestBody UserCredentials userCredentials) {
        boolean isAdded = userService.addUserCredentials(userCredentials);
        if (isAdded) {
            return new ResponseEntity<>(true, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<Boolean> changePassword(
            @RequestParam String email,
            @RequestParam String currentPassword,
            @RequestParam String newPassword) {

        boolean isChanged = userService.changePassword(email, currentPassword, newPassword);
        return new ResponseEntity<>(isChanged, isChanged ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    // Endpoint to verify login credentials
    @PostMapping("/login")
    public ResponseEntity<Boolean> verifyLogin(
            @RequestParam String email,
            @RequestParam String password) {

        boolean isVerified = userService.verifyLogin(email, password);
        return new ResponseEntity<>(isVerified, isVerified ? HttpStatus.OK : HttpStatus.UNAUTHORIZED);
    }

    // Endpoint for user signup
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserCredentials userCredentials) {
        boolean isRegistered = userService.registerUser(userCredentials);
        return new ResponseEntity<>(isRegistered ? "User registered successfully" : "Registration failed, email already exists",
                isRegistered ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST);
    }
}
