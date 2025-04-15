package com.luka.sneksibetting.models.gameMessages;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameStartNoBetMessage extends Message implements Serializable {
    private final String gameId;
    private final Integer[][] player1Board;
    private final Integer[][] player2Board;
    private final Integer player1Score;
    private final Integer player2Score;
    private final Integer player1HeadPosI;
    private final Integer player1HeadPosJ;
    private final Integer player2HeadPosI;
    private final Integer player2HeadPosJ;
    private final String player1Username;
    private final String player2Username;
    private final BigDecimal player1Elo;
    private final BigDecimal player2Elo;
    private final BigDecimal eloIfP1Wins;
    private final BigDecimal eloIfP1Lose;
    private final BigDecimal eloIfDraw;
    private final Integer numWatching;

    public GameStartNoBetMessage(String zson) throws Exception {
        super(4);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(zson, new TypeReference<Map<String, Object>>() {});

        this.gameId = (String) map.get("gameId");
        this.player1Username = (String) map.get("player1Username");
        this.player2Username = (String) map.get("player2Username");

        this.player1Score = (Integer) map.get("player1Score");
        this.player2Score = (Integer) map.get("player2Score");

        this.player1HeadPosI = (Integer) map.get("player1HeadPosI");
        this.player1HeadPosJ = (Integer) map.get("player1HeadPosJ");
        this.player2HeadPosI = (Integer) map.get("player2HeadPosI");
        this.player2HeadPosJ = (Integer) map.get("player2HeadPosJ");
        this.player1Elo = new BigDecimal(map.get("player1Elo").toString());
        this.player2Elo = new BigDecimal(map.get("player2Elo").toString());
        this.eloIfP1Wins = new BigDecimal(map.get("eloIfP1Wins").toString());
        this.eloIfP1Lose = new BigDecimal(map.get("eloIfP1Lose").toString());
        this.eloIfDraw = new BigDecimal(map.get("eloIfDraw").toString());

        this.numWatching = (Integer) map.get("numWatching");

        this.player1Board = convertTo2DIntArray(map.get("player1Board"));
        this.player2Board = convertTo2DIntArray(map.get("player2Board"));
    }

    private Integer[][] convertTo2DIntArray(Object obj) {
        List<List<Integer>> list = (List<List<Integer>>) obj;
        Integer[][] array = new Integer[list.size()][];
        for (int i = 0; i < list.size(); i++) {
            List<Integer> row = list.get(i);
            array[i] = row.toArray(new Integer[0]);
        }
        return array;
    }

    public String getGameId() {
        return gameId;
    }

    public GameStartNoBetMessage(String player1Username,
                                 String player2Username, String gameId, BigDecimal player1Elo, BigDecimal player2Elo) {
        super(3);
        this.player1Score = 0;
        this.player2Score = 0;
        this.player1HeadPosI = 5;
        this.player1HeadPosJ = 5;
        this.player2HeadPosI = 5;
        this.player2HeadPosJ = 5;
        this.player1Username = player1Username;
        this.player2Username = player2Username;
        this.gameId = gameId;
        this.player1Elo = player1Elo;
        this.player2Elo = player2Elo;
        player1Board = new Integer[10][];
        player2Board = new Integer[10][];
        for (int i = 0; i < 10; i++) {
            player1Board[i] = new Integer[10];
            player2Board[i] = new Integer[10];
            for (int j = 0; j < 10; j++) {
                player1Board[i][j] = 0;
                player2Board[i][j] = 0;
            }
        }
        player1Board[5][5] = 1;
        player2Board[5][5] = 1;
        player1Board[3][3] = 2;
        player2Board[3][3] = 2;
        this.eloIfP1Wins = new BigDecimal(10);
        this.eloIfP1Lose = new BigDecimal(10);
        this.eloIfDraw = new BigDecimal(0);
        this.numWatching = 0;
    }

    public String toZson() throws Exception {
        Map<String, Object> msgObj = new HashMap<>();
        msgObj.put("gameId", this.gameId);
        msgObj.put("player1Board", this.player1Board);
        msgObj.put("player2Board", this.player2Board);
        msgObj.put("player1HeadPosI", this.player1HeadPosI);
        msgObj.put("player1HeadPosJ", this.player1HeadPosJ);
        msgObj.put("player2HeadPosI", this.player2HeadPosI);
        msgObj.put("player2HeadPosJ", this.player2HeadPosJ);
        msgObj.put("player1Score", this.player1Score);
        msgObj.put("player2Score", this.player2Score);
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

    @Override
    public byte[] GetBytes() throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);

        dataOutputStream.writeInt(id);

        Map<String, Object> msgObj = new HashMap<>();
        msgObj.put("gameId", this.gameId);
        msgObj.put("player1Board", this.player1Board);
        msgObj.put("player2Board", this.player2Board);
        msgObj.put("player1HeadPosI", this.player1HeadPosI);
        msgObj.put("player1HeadPosJ", this.player1HeadPosJ);
        msgObj.put("player2HeadPosI", this.player2HeadPosI);
        msgObj.put("player2HeadPosJ", this.player2HeadPosJ);
        msgObj.put("player1Score", this.player1Score);
        msgObj.put("player2Score", this.player2Score);
        msgObj.put("player1Username", this.player1Username);
        msgObj.put("player2Username", this.player2Username);
        msgObj.put("player1Elo", this.player1Elo);
        msgObj.put("player2Elo", this.player2Elo);
        msgObj.put("eloIfP1Wins", this.eloIfP1Wins);
        msgObj.put("eloIfP1Lose", this.eloIfP1Lose);
        msgObj.put("eloIfDraw", this.eloIfDraw);
        msgObj.put("numWatching", this.numWatching);

        ObjectWriter objectWriter = new ObjectMapper().writer();
        String json = objectWriter.writeValueAsString(msgObj);

        dataOutputStream.writeUTF(json);

        return byteArrayOutputStream.toByteArray();
    }
}
