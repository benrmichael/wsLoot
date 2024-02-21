package com.loot.server.socket.game;

import com.loot.server.socket.domain.Player;
import com.loot.server.socket.game.cards.BaseCard;

public interface IGameSession {

    void playCard(Player player, BaseCard card);

    void dealInitialCards();

    BaseCard dealCard(Player player);

    Boolean readyPlayerUp(Player player);

    void addPlayer(Player player);
}
