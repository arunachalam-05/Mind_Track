package com.EEG_Project.Final.Year.Project.Controller;

import com.EEG_Project.Final.Year.Project.DTO.LoginDto;
import com.EEG_Project.Final.Year.Project.DTO.RegisterDto;
import com.EEG_Project.Final.Year.Project.Email.EmailDetails;
import com.EEG_Project.Final.Year.Project.Email.EmailService;
import com.EEG_Project.Final.Year.Project.Model.Users;
import com.EEG_Project.Final.Year.Project.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    private UserService userService;
    private EmailService emailService;
    public UserController (UserService userService, EmailService emailService){
        this.userService = userService;
        this.emailService = emailService;
    }

    @PostMapping("/register")

    public ResponseEntity<Map<String, String>> register(@RequestBody RegisterDto user) {
        String result = userService.registerUser(user.getUserName(), user.getName(), user.getMobileNumber(), user.getPassword(), user.getEmail());

        // Create a JSON response
        Map<String, String> response = new HashMap<>();
        response.put("message", result);

        if (result.equals("User Registered Successfully")){
            EmailDetails emailDetails = new EmailDetails();
            emailDetails.setRecipient(user.getEmail());
            emailDetails.setMsgBody("Dear " + user.getName() + ",\n\nThank you for registering on our platform!\n\nBest Regards,\nEEG Project Team");
            emailDetails.setSubject("Welcome to EEG Project");

            String emailResult = emailService.sendSimpleMail(emailDetails);
            System.out.println(emailResult);

        }


        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginDto user) {
        String result = userService.login(user);
        Map<String, Object> response = new HashMap<>();
        if (result.startsWith("Login Successful!")) {
            response.put("message", result); // "Login Successful!"
            response.put("id", result.split("!")[1].trim()); // Extract user ID
        } else {
            response.put("message", result);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<Users> getUserById(@PathVariable Long userId){
        Optional<Users> usersOptional = userService.findById(userId);
        return usersOptional.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

}
