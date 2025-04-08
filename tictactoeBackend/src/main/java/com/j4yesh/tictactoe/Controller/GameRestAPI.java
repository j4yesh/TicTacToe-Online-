package com.j4yesh.tictactoe.Controller;

import com.j4yesh.tictactoe.Model.AuthUser;
import com.j4yesh.tictactoe.Service.GameplayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/gamedata")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class GameRestAPI {
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyz";

    @Autowired
    GameplayService gameplayService;

    @GetMapping("/getgameid")
    String getGameId(){
        int length=5;
        StringBuilder sb = new StringBuilder(length);
        Random random=new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }

    @GetMapping("/players")
    ResponseEntity<?> getAllPlayers(){
        try{
            List<AuthUser> players=gameplayService.allPlayers();
            for(int i=0;i<players.size();i++){
                players.get(i).setPassword(null);
            }
            return ResponseEntity.ok().body(players);
        }catch (Exception e){
            return  ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

}
