package com.j4yesh.tictactoe.Model;

import com.j4yesh.tictactoe.Controller.GameHandler;
import com.j4yesh.tictactoe.Service.ScoreTrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Arrays;

public class GameSession {
    private final WebSocketSession player1;
    private final WebSocketSession player2;
    private String[][] board = { {"", "", ""}, {"", "", ""}, {"", "", ""} };
    private WebSocketSession currentTurn;


    @Autowired
    private ScoreTrackerService scoreTrackerService;


    public WebSocketSession getPlayer1() {
        return player1;
    }

    public WebSocketSession getPlayer2() {
        return player2;
    }



    public GameSession(WebSocketSession p1, WebSocketSession p2, ScoreTrackerService scoreTrackerService) {
        this.player1 = p1;
        this.player2 = p2;
        this.currentTurn = player1;
        this.scoreTrackerService = scoreTrackerService;
        sendMessage(player1, "Game started! You are X. Your turn.");
        sendMessage(player2, "Game started! You are O. Waiting for opponent.");
    }

    public void processMove(WebSocketSession session, String positionStr) {
        if (session != currentTurn) {
            sendMessage(session, "Not your turn.");
            return;
        }

        String[] parts = positionStr.split(",");
        if (parts.length != 2) {
            sendMessage(session, "Invalid move format.");
            return;
        }

        int row, col;
        try {
            row = Integer.parseInt(parts[0]);
            col = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            sendMessage(session, "Invalid move coordinates.");
            return;
        }

        if (row < 0 || row >= 3 || col < 0 || col >= 3 || !board[row][col].equals("")) {
            sendMessage(session, "Invalid move. Try again.");
            return;
        }

        String symbol = (session == player1) ? "X" : "O";
        board[row][col] = symbol;
        broadcast("Move: " + row + "," + col + "," + symbol);

        if (checkWin(symbol)) {
            broadcast("Game over! " + symbol + " wins!");
            String player1username=GameHandler.socketIdToUsername.get(player1.getId());
            String player2username=GameHandler.socketIdToUsername.get(player2.getId());

            scoreTrackerService.increaseWin((this.currentTurn==player1)?player1username:player2username);
            scoreTrackerService.increaseLoss((this.currentTurn==player1)?player2username:player1username);

//            resetGame();
            return;
        }

        if (checkDraw()) {
            broadcast("Game over! It's a draw!");
//            resetGame();
            return;
        }

        currentTurn = (currentTurn == player1) ? player2 : player1;
        sendMessage(currentTurn, "Your turn.");
    }

    private boolean checkWin(String symbol) {
        for (int i = 0; i < 3; i++) {
            if (Arrays.equals(board[i], new String[]{symbol, symbol, symbol})) return true;
            if (board[0][i].equals(symbol) && board[1][i].equals(symbol) && board[2][i].equals(symbol)) return true;
        }
        return (board[0][0].equals(symbol) && board[1][1].equals(symbol) && board[2][2].equals(symbol)) ||
                (board[0][2].equals(symbol) && board[1][1].equals(symbol) && board[2][0].equals(symbol));
    }

    private boolean checkDraw() {
        for (String[] row : board) {
            for (String cell : row) {
                if (cell.equals("")) return false;
            }
        }
        return true;
    }

    public void resetGame() {
        board = new String[][]{{"", "", ""}, {"", "", ""}, {"", "", ""}};
        currentTurn = player1;
        sendMessage(player1, "New game! You are X. Your turn.");
        sendMessage(player2, "New game! You are O. Waiting for opponent.");
    }

    private void sendMessage(WebSocketSession session, String message) {
        try {
            session.sendMessage(new TextMessage(message));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void broadcast(String message) {
        sendMessage(player1, message);
        sendMessage(player2, message);
    }

    public void endGame(WebSocketSession disconnectedPlayer) {
        WebSocketSession opponent = (disconnectedPlayer == player1) ? player2 : player1;
        sendMessage(opponent, "Opponent disconnected. You win!");
    }
}
