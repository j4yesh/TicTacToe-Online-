package com.j4yesh.tictactoe.Service;

import com.j4yesh.tictactoe.Model.AuthUser;
import com.j4yesh.tictactoe.Repository.UserRepo;
import com.j4yesh.tictactoe.Utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;

    public AuthService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public String registerUser(AuthUser user) {
        Optional<AuthUser> existingUser = userRepo.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            throw new RuntimeException("user already exists.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return "user registered successfully.";
    }

    public String loginUser(String username, String password) {
        AuthUser user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("user not found"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("invalid credentials");
        }
        return jwtUtil.generateToken(username);
    }

}
