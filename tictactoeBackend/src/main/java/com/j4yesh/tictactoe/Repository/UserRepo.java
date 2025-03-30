package com.j4yesh.tictactoe.Repository;

import com.j4yesh.tictactoe.Model.AuthUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepo extends MongoRepository<AuthUser, String> {
    Optional<AuthUser> findByUsername(String username);
}
