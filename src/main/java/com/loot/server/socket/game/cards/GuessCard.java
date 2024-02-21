package com.loot.server.socket.game.cards;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GuessCard extends BaseCard {

    @JsonProperty
    private String playedOn;

    @JsonProperty
    private String cardGuessed;

    public GuessCard(int power, String name, String description, String playedOn, String cardGuessed){
        super(power, name, description);
        this.playedOn = playedOn;
        this.cardGuessed = cardGuessed;
    }
}
