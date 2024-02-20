package com.loot.server.socket;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

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

    private Map<String, Set<String>> gameSessions = new HashMap<>();

    @MessageMapping("/createGame")
    public void createGame(CreateGameRequest request) throws Exception {
        String roomKey = request.getRoomKey();
        gameSessions.put(roomKey, new HashSet<>());

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
        Set<String> players = gameSessions.get(roomKey);
        if (players != null) {
            players.add(playerId);
            messagingTemplate.convertAndSend("/topic/gameStatus/" + roomKey,
                    GameStatus.builder().message("Player joined: " + playerId).build());
        } else {
            messagingTemplate.convertAndSendToUser(playerId, "/topic/error", "Game session not found: " + roomKey);
        }

        // Debugging purposes
        printTheCurrentRoomData();
    }

    private void printTheCurrentRoomData() {
        for(String roomKey : gameSessions.keySet()){
            System.out.println("RoomKey(" + roomKey + ") has players: " + gameSessions.get(roomKey).toString());
        }
    }
}
