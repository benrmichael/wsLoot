package com.loot.server.api;

import com.loot.server.api.domain.GameCreationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.loot.server.api.service.GameService;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RestController
public class Controller {

    @Autowired
    private GameService gameService;
    
    @GetMapping(value = "/game/create")
    public ResponseEntity<?> createNewGameRoom() {
        GameCreationDto createdGame = gameService.getRoomKeyForNewGame();
        return new ResponseEntity<GameCreationDto>(createdGame, HttpStatus.OK);
    }

    @GetMapping(value = "/game/validate")
    public ResponseEntity<?> checkIfValidRoomKey(@RequestParam("key") String roomKey) {
        String decodedKey = URLDecoder.decode(roomKey, StandardCharsets.UTF_8);
        if(gameService.isValidRoomKey(decodedKey)){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
