package com.loot.server.socket.game;

import com.loot.server.socket.domain.Player;
import com.loot.server.socket.game.cards.BaseCard;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
public class PlayerHandler implements IPlayerHandler {

    private List<Player> playersInRoom;
    private List<Player> playersInRound;
    private List<Player> readyPlayers;
    private int playerTurnIndex;

    Map<Player, List<BaseCard>> playedCards;
    Map<Player, Integer> numberOfWins;

    private int numberOfPlayersForGame = 4;
    private int numberOfWinsNeeded = 3;

    @Override
    public void addPlayer(Player player) {
        playersInRoom.add(player);
    }

    @Override
    public Boolean hasNextPlayer() {
        return !playersInRound.isEmpty();
    }

    @Override
    public Player getNextPlayer() {
        Player player = playersInRound.get(playerTurnIndex);
        if(++playerTurnIndex >= playersInRound.size()) {
            playerTurnIndex = 0;
        }

        return player;
    }

    @Override
    public Boolean addWinToPlayer(Player player) {
        int currentNumberOfWins = numberOfWins.get(player) + 1;
        if(currentNumberOfWins == numberOfWinsNeeded) {
            return true;
        }

        numberOfWins.put(player, currentNumberOfWins);
        return false;
    }

    @Override
    public void addPlayedCard(Player player, BaseCard card) {
        playedCards.get(player).add(card);
    }

    @Override
    public Boolean readyUp(Player player) {
        readyPlayers.add(player);
        if(readyPlayers.size() == numberOfPlayersForGame) {
            startNewRound();
            return true;
        }
        return false;
    }

    @Override
    public void removePlayerFromRound(Player player) {
        playersInRound.remove(player);
    }

    @Override
    public void startNewRound() {
        // TODO : For the future, the starting player should be the one who just won, not the host
        playerTurnIndex = 0;
        playedCards = new HashMap<>();
        numberOfWins = new HashMap<>();
        for(Player readyPlayer : readyPlayers) {
            playedCards.put(readyPlayer, new ArrayList<>());
            numberOfWins.put(readyPlayer, 0);
        }
    }

}
