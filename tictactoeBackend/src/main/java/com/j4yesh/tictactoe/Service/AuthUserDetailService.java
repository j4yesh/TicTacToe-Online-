package com.j4yesh.tictactoe.Service;

import com.j4yesh.tictactoe.Model.AuthUser;
import com.j4yesh.tictactoe.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthUserDetailService implements UserDetailsService {

    @Autowired
    UserRepo userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AuthUser> authUser = userRepository.findByUsername(username);
        System.out.println("auth userdetaill serviec");
        if (!authUser.isPresent()) {
            throw new UsernameNotFoundException(username);
        } else {
            return User.builder()
                    .username(authUser.get().getUsername())
                    .password(authUser.get().getPassword())
//                    .disabled(!authUser.get().isActive())
                    .build();
        }
    }

    public String getUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = ((UserDetails) principal).getUsername();
        return userId;
    }

    public AuthUser getAuthUser(String username){
        return userRepository.findByUsername(username).get();
    }

    public AuthUser getAuthUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = ((UserDetails) principal).getUsername();
        AuthUser authUser=userRepository.findByUsername(userId).get();
        return authUser;
    }
}