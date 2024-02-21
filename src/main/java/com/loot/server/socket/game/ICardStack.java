package com.loot.server.socket.game;

import com.loot.server.socket.game.cards.BaseCard;

public interface ICardStack {

    void shuffle();

    BaseCard drawCard();

    Boolean isDeckEmpty();

}
