package com.luka.sneksibetting.models.snake;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.luka.sneksibetting.models.gameMessages.GameStartNoBetMessage;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class GameState {
    private final String gameId;
    private final Board player1Board;
    private final Board player2Board;
    private final String player1Username;
    private final String player2Username;
    private final BigDecimal player1Elo;
    private final BigDecimal player2Elo;
    private final BigDecimal eloIfP1Wins;
    private final BigDecimal eloIfP1Lose;
    private final BigDecimal eloIfDraw;
    private final Integer numWatching;

    public String getPlayer1Username() {
        return player1Username;
    }

    public String getPlayer2Username() {
        return player2Username;
    }

    public Integer getNumWatching() {
        return numWatching;
    }

    public Board getPlayer1Board() {
        return player1Board;
    }

    public Board getPlayer2Board() {
        return player2Board;
    }

    public String getGameId() {
        return gameId;
    }

    public GameState(GameStartNoBetMessage msg) {
        this.gameId = msg.getGameId();
        this.player1Username = msg.getPlayer1Username();
        this.player2Username = msg.getPlayer2Username();
        this.player1Elo = msg.getPlayer1Elo();
        this.player2Elo = msg.getPlayer2Elo();
        this.eloIfP1Lose = msg.getEloIfP1Lose();
        this.eloIfP1Wins = msg.getEloIfP1Wins();
        this.eloIfDraw = msg.getEloIfDraw();
        this.numWatching = 0;
        this.player1Board = new Board();
        this.player2Board = new Board();

    }

    public GameState(String zson) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(zson, new TypeReference<Map<String, Object>>() {});

        this.gameId = (String) map.get("gameId");
        this.player1Username = (String) map.get("player1Username");
        this.player2Username = (String) map.get("player2Username");

        this.player1Elo = new BigDecimal(map.get("player1Elo").toString());
        this.player2Elo = new BigDecimal(map.get("player2Elo").toString());
        this.eloIfP1Wins = new BigDecimal(map.get("eloIfP1Wins").toString());
        this.eloIfP1Lose = new BigDecimal(map.get("eloIfP1Lose").toString());
        this.eloIfDraw = new BigDecimal(map.get("eloIfDraw").toString());

        this.numWatching = (Integer) map.get("numWatching");

        this.player1Board = new Board((String)map.get("player1Board"));
        this.player2Board = new Board((String)map.get("player2Board"));
    }


    public GameState(String gameId, String player1Username, String player2Username, BigDecimal player1Elo, BigDecimal player2Elo,
                     BigDecimal eloIfP1Wins, BigDecimal eloIfP1Lose, BigDecimal eloIfDraw) {
        this.gameId = gameId;
        this.player1Username = player1Username;
        this.player2Username = player2Username;
        this.player1Elo = player1Elo;
        this.player2Elo = player2Elo;
        this.eloIfDraw = eloIfDraw;
        this.eloIfP1Wins = eloIfP1Wins;
        this.eloIfP1Lose = eloIfP1Lose;
        this.numWatching = 0;
        this.player1Board = new Board();
        this.player2Board = new Board();
    }

    public String toZson() throws Exception {
        Map<String, Object> msgObj = new HashMap<>();
        msgObj.put("gameId", this.gameId);
        msgObj.put("player1Board", this.player1Board.ToZson());
        msgObj.put("player2Board", this.player2Board.ToZson());
        msgObj.put("player1Username", this.player1Username);
        msgObj.put("player2Username", this.player2Username);
        msgObj.put("player1Elo", this.player1Elo);
        msgObj.put("player2Elo", this.player2Elo);
        msgObj.put("eloIfP1Wins", this.eloIfP1Wins);
        msgObj.put("eloIfP1Lose", this.eloIfP1Lose);
        msgObj.put("eloIfDraw", this.eloIfDraw);
        msgObj.put("numWatching", this.numWatching);

        ObjectWriter objectWriter = new ObjectMapper().writer();

        return objectWriter.writeValueAsString(msgObj);
    }
}
