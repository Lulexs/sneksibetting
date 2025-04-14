package com.luka.sneksibetting.models.gameMessages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.math.BigDecimal;

public class GameStartNoBetMessage extends Message {
    private final String gameId;
    private final Integer[][] player1Board;
    private final Integer[][] player2Board;
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

    public GameStartNoBetMessage(String player1Username,
                                 String player2Username, String gameId, BigDecimal player1Elo, BigDecimal player2Elo) {
        super(3);
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
        }
        this.eloIfP1Wins = new BigDecimal(10);
        this.eloIfP1Lose = new BigDecimal(10);
        this.eloIfDraw = new BigDecimal(0);
        this.numWatching = 0;
    }

    @Override
    public byte[] GetBytes() throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);

        dataOutputStream.writeInt(id);

        var msgObj = new Object() {
            final String gameId = GameStartNoBetMessage.this.gameId;
            final Integer[][] player1Board = GameStartNoBetMessage.this.player1Board;
            final Integer[][] player2Board = GameStartNoBetMessage.this.player2Board;
            final Integer player1HeadPosI = GameStartNoBetMessage.this.player1HeadPosI;
            final Integer player1HeadPosJ = GameStartNoBetMessage.this.player1HeadPosJ;
            final Integer player2HeadPosI = GameStartNoBetMessage.this.player2HeadPosI;
            final Integer player2HeadPosJ = GameStartNoBetMessage.this.player2HeadPosJ;
            final String player1Username = GameStartNoBetMessage.this.player1Username;
            final String player2Username = GameStartNoBetMessage.this.player2Username;
            final BigDecimal player1Elo = GameStartNoBetMessage.this.player1Elo;
            final BigDecimal player2Elo = GameStartNoBetMessage.this.player2Elo;
            final BigDecimal eloIfP1Wins = GameStartNoBetMessage.this.eloIfP1Wins;
            final BigDecimal eloIfP1Lose = GameStartNoBetMessage.this.eloIfP1Lose;
            final BigDecimal eloIfDraw = GameStartNoBetMessage.this.eloIfDraw;
            final Integer numWatching = GameStartNoBetMessage.this.numWatching;
        };

        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = objectWriter.writeValueAsString(msgObj);

        dataOutputStream.writeUTF(json);

        return byteArrayOutputStream.toByteArray();
    }
}
