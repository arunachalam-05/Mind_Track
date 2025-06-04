package com.EEG_Project.Final.Year.Project.Service;

import com.EEG_Project.Final.Year.Project.DTO.LoginDto;
import com.EEG_Project.Final.Year.Project.Model.Users;
import com.EEG_Project.Final.Year.Project.Repository.UserRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepo repo;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private UserService (UserRepo userRepo){
        this.repo = userRepo;
    }

    public String registerUser(String userName, String name, double mobileNumber, String password, String email){
        if (repo.findByUserName(userName).isPresent()){
            return "Username already Exists";
        }
        if(repo.findByEmail(email).isPresent()){
            return "Email Already Exists";
        }
        Users newUser = new Users();
        newUser.setUserName(userName);
        newUser.setName(name);
        newUser.setMobileNumber(mobileNumber);
        newUser.setEmail(email);
        newUser.setPasssword(passwordEncoder.encode(password));

        repo.save(newUser);
        return "User Registered Successfully";
    }

    public String login(LoginDto loginDto) {
        Optional<Users> usersOptional;
        if (loginDto.getUserName() != null && !loginDto.getUserName().isEmpty()){
            usersOptional = repo.findByUserName(loginDto.getUserName());
        }else if (loginDto.getEmail() != null && !loginDto.getEmail().isEmpty()){
            usersOptional = repo.findByEmail(loginDto.getEmail());
        }else {
            return "Invalid login Credentials";
        }
        if (usersOptional.isEmpty()){
            return "user not found";
        }
        Users users = usersOptional.get();
        if(!passwordEncoder.matches(loginDto.getPassword(), users.getPassword())){
            return "Invalid Password";
        }
        return "Login Successful!" + users.getId();
    }


    public Optional<Users> findById(Long userId) {
        return repo.findById(userId);
    }
}
