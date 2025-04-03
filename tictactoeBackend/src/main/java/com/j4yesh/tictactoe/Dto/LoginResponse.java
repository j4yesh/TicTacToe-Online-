package com.j4yesh.tictactoe.Dto;

public class LoginResponse {
    String username;
    int winCnt;

    public int getLossCnt() {
        return lossCnt;
    }

    public void setLossCnt(int lossCnt) {
        this.lossCnt = lossCnt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getWinCnt() {
        return winCnt;
    }

    public void setWinCnt(int winCnt) {
        this.winCnt = winCnt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    int lossCnt;
    String token;

    public LoginResponse(String username, int winCnt, int lossCnt, String token){
        this.username=username;
        this.lossCnt=lossCnt;
        this.winCnt=winCnt;
        this.token=token;
    }
}
