package com.j4yesh.tictactoe.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j4yesh.tictactoe.Model.GameSession;
import com.j4yesh.tictactoe.Service.AuthUserDetailService;
import com.j4yesh.tictactoe.Service.ScoreTrackerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.SessionSynchronization;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class GameHandler extends TextWebSocketHandler {

    @Autowired
    private AuthUserDetailService authUserDetailService;

    @Autowired
    private ScoreTrackerService scoreTrackerService;

    private static final Map<String, WebSocketSession> players = new ConcurrentHashMap<>();
    private static final Map<String, GameSession> games = new ConcurrentHashMap<>();
    public static final Map<String, String> socketIdToUsername = new ConcurrentHashMap<>();
    private static final Map<String, WebSocketSession> gameIdFirstPlayer = new ConcurrentHashMap<>();

    private static String getUsernameWithSocket(WebSocketSession session) {
        if (session.getUri() == null || session.getUri().getPath().isEmpty()) {
            return "";
        }
        String path = session.getUri().getPath();
        String[] parts = path.split("/");

        if (parts.length < 3) {
            return ""; // Ensure there's a username in the path
        }
        return parts[parts.length - 2]; // Extract username correctly
    }



    private static String getRoomId(WebSocketSession session) {
        if (session.getUri() == null || session.getUri().getPath().isEmpty()) {
            //.warn("Session URI is null or empty.");
            return "";
        }
        String path = session.getUri().getPath();
        return path.substring(path.lastIndexOf('/') + 1);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        players.put(session.getId(), session);
        String username = getUsernameWithSocket(session);
        String roomId = getRoomId(session);
        System.out.println(session.getUri());
        System.out.println("username: "+username);
        System.out.println("roomId: "+roomId);
        socketIdToUsername.put(session.getId(), username);

        if (gameIdFirstPlayer.containsKey(roomId)) {
            WebSocketSession firstPlayerSession = gameIdFirstPlayer.remove(roomId);
            if (firstPlayerSession != null) {
                System.out.println("Initiating the game");
                createGame(firstPlayerSession.getId(), session.getId());
            }
        } else {
            gameIdFirstPlayer.put(roomId, session);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        String payload = message.getPayload();
        System.out.println("Received raw payload: " + payload); // Debugging

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(payload);
            System.out.println("Parsed JSON: " + jsonNode.toString()); // Debugging

            if (!jsonNode.has("type") || !jsonNode.has("position")) {
                System.out.println("Invalid move data: " + payload);
                return;
            }

            String type = jsonNode.get("type").asText();
            if ("move".equals(type)) {
                String position = jsonNode.get("position").asText();
                GameSession game = findGameByPlayer(session.getId());
                if (game != null) {
                    game.processMove(session, position);
                }
            }
            if("reset".equals(type)){
                GameSession game = findGameByPlayer(session.getId());
                if (game != null) {
                    game.resetGame();
                }
            }
        } catch (Exception e) {
            System.out.println("Invalid JSON format: " + payload);
            e.printStackTrace();
        }
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        players.remove(session.getId());
        socketIdToUsername.remove(session.getId());
        GameSession game = findGameByPlayer(session.getId());
        if (game != null) {
            game.endGame(session);
            games.remove(game.getPlayer1());
            games.remove(game.getPlayer2());
            gameIdFirstPlayer.remove(getRoomId(session));
        }
    }

    private void createGame(String player1, String player2) {
        WebSocketSession session1 = players.get(player1);
        WebSocketSession session2 = players.get(player2);
        if (session1 == null || session2 == null) {
            //.warn("One or both players have disconnected before game creation.");
            System.out.println("One or both players have disconnected before game creation.");
            return;
        }
        GameSession gameSession = new GameSession(session1, session2, scoreTrackerService);
        games.put(player1, gameSession);
        games.put(player2, gameSession);
    }

    private GameSession findGameByPlayer(String playerId) {
        return games.get(playerId);
    }
}



