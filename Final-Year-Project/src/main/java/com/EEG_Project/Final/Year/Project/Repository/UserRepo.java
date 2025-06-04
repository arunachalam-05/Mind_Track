package com.EEG_Project.Final.Year.Project.Repository;

import com.EEG_Project.Final.Year.Project.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<Users, Long> {
    Optional<Users> findByUserName(String userName);
    Optional<Users> findByMobileNumber(double mobileNumber);

    Optional<Users> findByEmail(String email);
}
