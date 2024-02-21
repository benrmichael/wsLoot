package com.loot.server.socket.game;

import com.loot.server.socket.domain.Player;
import com.loot.server.socket.game.cards.BaseCard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameSession implements IGameSession{

    private List<String> players;
    private String roomKey;

    private CardStack cardStack = new CardStack();
    private PlayerHandler playerHandler = new PlayerHandler();

    @Override
    public void playCard(Player player, BaseCard card) {

    }

    @Override
    public void dealInitialCards() {

    }

    @Override
    public BaseCard dealCard(Player player) {
        return null;
    }

    @Override
    public Boolean readyPlayerUp(Player player) {
        return playerHandler.readyUp(player);
    }

    @Override
    public void addPlayer(Player player) {
        playerHandler.addPlayer(player);
    }
}
