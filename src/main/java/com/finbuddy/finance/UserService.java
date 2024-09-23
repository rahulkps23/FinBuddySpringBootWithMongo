package com.finbuddy.finance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<UserCredentials> findAllUserCredentials() {
        return userRepository.findAll();
    }

    public boolean addUserCredentials(UserCredentials userCredentials){
        try {
            // Hash the password before saving
            userCredentials.setPassword(passwordEncoder.encode(userCredentials.getPassword()));
            // Save the user credentials to the repository
            UserCredentials savedUser = userRepository.save(userCredentials);

            // Check if the saved user is not null and has a valid ObjectId (to ensure it's saved)
            return savedUser != null && savedUser.getId() != null;
        } catch (Exception e) {
            // If any exception occurs, return false
            return false;
        }
    }

    public boolean changePassword(String email, String currentPassword, String newPassword) {
        Optional<UserCredentials> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            UserCredentials user = userOpt.get();
            // Check if the current password matches
            if (passwordEncoder.matches(currentPassword, user.getPassword())) {
                user.setPassword(passwordEncoder.encode(newPassword)); // Hash the new password
                userRepository.save(user); // Save the updated user
                return true; // Password changed successfully
            }
        }
        return false; // Password change failed
    }

    // Method to verify user credentials
    public boolean verifyLogin(String email, String password) {
        // Fetch the user credentials by email
        Optional<UserCredentials> userCredentialsOpt = userRepository.findByEmail(email);

        // If user exists, check password
        if (userCredentialsOpt.isPresent()) {
            UserCredentials userCredentials = userCredentialsOpt.get();
            // Compare the provided password with the stored password
            return passwordEncoder.matches(password, userCredentials.getPassword());
        }
        return false; // If user does not exist or password is wrong
    }

    // Method to register a new user
    public boolean registerUser(UserCredentials userCredentials) {
        try {
            // Check if the email already exists in the database
            if (userRepository.findByEmail(userCredentials.getEmail()).isPresent()) {
                return false; // Email already exists
            }
            // Encode the password before saving
            userCredentials.setPassword(passwordEncoder.encode(userCredentials.getPassword()));
            // Save the user in the database
            UserCredentials savedUser = userRepository.save(userCredentials);
            // Check if the saved user is not null and has a valid ObjectId (to ensure it's saved)
            return savedUser != null && savedUser.getId() != null;
        } catch (Exception e) {
            // If any exception occurs, return false
            return false;
        }
    }
}
