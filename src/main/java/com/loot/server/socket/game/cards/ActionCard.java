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
public class ActionCard extends BaseCard {

    @JsonProperty
    private String playedOn;

    public ActionCard(int power, String name, String description, String playedOn) {
        super(power, name, description);
        this.playedOn = playedOn;
    }
}
