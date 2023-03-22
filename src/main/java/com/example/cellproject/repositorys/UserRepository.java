package com.example.cellproject.repositorys;

import com.example.cellproject.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsrId(String usrId);
}
