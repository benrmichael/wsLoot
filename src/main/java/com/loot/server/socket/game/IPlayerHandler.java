package com.loot.server.socket.game;

import com.loot.server.socket.domain.Player;
import com.loot.server.socket.game.cards.BaseCard;

public interface IPlayerHandler {

    void addPlayer(Player player);

    Boolean hasNextPlayer();

    Player getNextPlayer();

    Boolean addWinToPlayer(Player player);

    void addPlayedCard(Player player, BaseCard card);

    Boolean readyUp(Player player);

    void removePlayerFromRound(Player player);

    void startNewRound();
}
