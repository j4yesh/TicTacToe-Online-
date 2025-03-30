package com.j4yesh.tictactoe.Service;


import com.j4yesh.tictactoe.Model.AuthUser;
import com.j4yesh.tictactoe.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameplayService {
    @Autowired
    UserRepo userRepo;

    public List<AuthUser> allPlayers(){
        return userRepo.findAll();
    }
}
