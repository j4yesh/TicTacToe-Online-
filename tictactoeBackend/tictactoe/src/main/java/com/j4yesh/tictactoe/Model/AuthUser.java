package com.j4yesh.tictactoe.Model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "users")
public class AuthUser {
    private String password;
    @Id
    private String username;

    private int winCnt,lossCnt;


    public int getWinCnt(){
        return this.winCnt;
    }

    public void setWinCnt(int winCnt){
        this.winCnt=winCnt;
    }

    public int getLossCnt(){
        return this.lossCnt;
    }

    public void setLossCnt(int lossCnt){
        this.lossCnt=lossCnt;
    }
//    private List<String> friends=new ArrayList<>();

//    public List<String> getFriends(){
//        return this.friends;
//    }
//
//    public void setFriends(List<String>friends){
//        this.friends=friends;
//    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
