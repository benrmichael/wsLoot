package com.loot.server.socket.game.cards;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegularCard extends BaseCard {

    public RegularCard(int power, String name, String description){
        super(power, name, description);
    }

}
