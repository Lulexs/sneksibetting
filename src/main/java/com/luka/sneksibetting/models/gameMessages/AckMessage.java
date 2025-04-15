package com.luka.sneksibetting.models.gameMessages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AckMessage extends Message {
    private String gameId;
    private Boolean isPlayer1;

    @JsonCreator
    public AckMessage(@JsonProperty("gameId") String gameId,
                      @JsonProperty("isPlayer1") Boolean isPlayer1) {
        super(6);
        this.gameId = gameId;
        this.isPlayer1 = isPlayer1;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    @Override
    public byte[] GetBytes() throws Exception {
        return new byte[0];
    }

    public Boolean getPlayer1() {
        return isPlayer1;
    }

    public void setPlayer1(Boolean player1) {
        isPlayer1 = player1;
    }
}
