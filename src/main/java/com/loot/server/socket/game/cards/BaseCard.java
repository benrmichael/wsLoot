package com.loot.server.socket.game.cards;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = RegularCard.class, name = "regular"),
        @JsonSubTypes.Type(value = ActionCard.class, name = "action"),
        @JsonSubTypes.Type(value = GuessCard.class, name = "guess")
})
public abstract class BaseCard {

    private int power;

    private String name;

    private String description;
}
