package com.luka.sneksibetting.models.gameMessages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SnakeMoveMessage extends Message {
    private String gameId;
    private Boolean isPlayer1;
    private Character direction;

    @JsonCreator
    public SnakeMoveMessage(@JsonProperty("gameId")String gameId, @JsonProperty("isPlayer1")Boolean isPlayer1, @JsonProperty("direction")Character direction) {
        super(5);
        this.gameId = gameId;
        this.isPlayer1 = isPlayer1;
        this.direction = direction;
    }

    @Override
    public byte[] GetBytes() throws Exception {
        return new byte[0];
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public Boolean getPlayer1() {
        return isPlayer1;
    }

    public void setPlayer1(Boolean player1) {
        isPlayer1 = player1;
    }

    public Character getDirection() {
        return direction;
    }

    public void setDirection(Character direction) {
        this.direction = direction;
    }
}
