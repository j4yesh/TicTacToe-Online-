package com.j4yesh.tictactoe.Service;

import com.j4yesh.tictactoe.Model.AuthUser;
import com.j4yesh.tictactoe.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScoreTrackerService {
    @Autowired
    UserRepo userRepo;

    public void increaseWin(String player1){
        try{
            AuthUser authUser = userRepo.findByUsername(player1).get();
            authUser.setWinCnt(authUser.getWinCnt()+1);
            userRepo.save(authUser);
        }catch (Exception e){
            System.out.println(e.getMessage());

        }
    }

    public void increaseLoss(String player){
        try{
            AuthUser authUser = userRepo.findByUsername(player).get();
            authUser.setLossCnt(authUser.getLossCnt()+1);
            userRepo.save(authUser);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
