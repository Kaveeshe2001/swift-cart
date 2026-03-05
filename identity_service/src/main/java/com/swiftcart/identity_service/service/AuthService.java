package com.swiftcart.identity_service.service;

import com.swiftcart.identity_service.client.NotificationClient;
import com.swiftcart.identity_service.client.NotificationRequest;
import com.swiftcart.identity_service.client.NotificationType;
import com.swiftcart.identity_service.dtos.AuthRequest;
import com.swiftcart.identity_service.dtos.RegisterRequest;
import com.swiftcart.identity_service.exceptions.AuthException;
import com.swiftcart.identity_service.mapper.UserMapper;
import com.swiftcart.identity_service.model.User;
import com.swiftcart.identity_service.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    private final NotificationClient notificationClient;

    public AuthService(UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService, UserMapper userMapper, NotificationClient notificationClient) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userMapper = userMapper;
        this.notificationClient = notificationClient;
    }

    public String saveUser(RegisterRequest request) {
        if (repository.findByName(request.name()).isPresent()) {
            throw new AuthException("Username already exists");
        }

        User credential = userMapper.toEntity(request);
        // Encrypt the password before saving to the database
        credential.setPassword(passwordEncoder.encode(request.password()));
        repository.save(credential);

        // Send the Welcome Email!
        try {
            NotificationRequest notification = new NotificationRequest();

            // Note: Make sure your User entity has getEmail() and getName() methods!
            notification.setRecipientEmail(credential.getEmail());
            notification.setSubject("Welcome to SwiftCart!");

            String welcomeMsg = "Hi " + credential.getName() + ",\n\n" +
                    "Welcome to SwiftCart! We are thrilled to have you. " +
                    "Start browsing our latest products today!";

            notification.setMessage(welcomeMsg);
            notification.setType(NotificationType.WELCOME_MESSAGE);

            // Fire it to the Notification Service
            notificationClient.sendNotification(notification);
            System.out.println("Welcome email triggered for: " + credential.getEmail());

        } catch (Exception e) {
            // Catches the error so registration still succeeds even if mail server fails
            System.out.println("Could not send welcome email: " + e.getMessage());
        }

        return "User registered successfully";
    }

    public String generateToken(AuthRequest request) {
        // Find the user
        User user = repository.findByName(request.username())
                .orElseThrow(() -> new AuthException("Invalid username or password"));

        // Check if the raw password matches the hashed password in the DB
        if (passwordEncoder.matches(request.password(), user.getPassword())) {
            // Generate and return the JWT
            return jwtService.generateToken(request.username());
        } else {
            throw new AuthException("Invalid username or password");
        }
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }

    public com.swiftcart.identity_service.dtos.UserResponse getUserById(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new AuthException("User not found with ID: " + id));

        com.swiftcart.identity_service.dtos.UserResponse response = new com.swiftcart.identity_service.dtos.UserResponse();

        // Ensure these match your actual User entity getters!
        response.setEmail(user.getEmail());
        response.setUsername(user.getName());

        return response;
    }
}