package com.loot.server.socket;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.loot.server.socket.domain.Player;
import com.loot.server.socket.game.GameSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loot.server.socket.domain.CreateGameRequest;
import com.loot.server.socket.domain.GameStatus;
import com.loot.server.socket.domain.JoinGameRequest;

@Controller
@ComponentScan
public class GameController {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private final Map<String, GameSession> gameSessions = new HashMap<>();

    @MessageMapping("/createGame")
    public void createGame(CreateGameRequest request) throws Exception {
        String roomKey = request.getRoomKey();
        gameSessions.put(roomKey, new GameSession());

        String body = mapper.writeValueAsString(GameStatus.builder().message("Game created: " + roomKey).build());
        messagingTemplate.convertAndSend("/topic/gameStatus/" + roomKey, body);
        joinGame(JoinGameRequest.builder().playerId(request.getPlayerId()).roomKey(roomKey).build());

        // Debugging purposes
        printTheCurrentRoomData();
    }

    @MessageMapping("/joinGame")
    public void joinGame(JoinGameRequest request) {
        String roomKey = request.getRoomKey();
        String playerId = request.getPlayerId();
        GameSession gameSession = gameSessions.get(roomKey);
        if (gameSession != null) {
            gameSession.addPlayer(new Player(playerId));
            messagingTemplate.convertAndSend("/topic/gameStatus/" + roomKey,
                    GameStatus.builder().message("Player joined: " + playerId).build());
        } else {
            messagingTemplate.convertAndSend("/topic/error", "Game session not found: " + roomKey);
        }

        // Debugging purposes
        printTheCurrentRoomData();
    }

    @MessageMapping("/ready")
    public void startGame(JoinGameRequest request) throws Exception {
        String roomKey = request.getRoomKey();
        GameSession gameSession = gameSessions.get(roomKey);
        if(gameSession != null){
            boolean everyoneReady = gameSession.readyPlayerUp(new Player(request.getPlayerId()));
            String message = everyoneReady ? "Everyone is ready to play!" : "Player - " + request.getPlayerId() + " is ready to begin!";
            String json = mapper.writeValueAsString(
                    GameStatus.builder()
                    .message(message)
                    .build()
            );
            messagingTemplate.convertAndSend("/topic/gameStatus/" + roomKey, json);
        } else {
            System.out.println("No players! Room key = " + request.getRoomKey());
        }
    }

    private void printTheCurrentRoomData() {
        for(String roomKey : gameSessions.keySet()){
            System.out.println("RoomKey(" + roomKey + ") has players: " + gameSessions.get(roomKey).toString());
        }
    }
}
