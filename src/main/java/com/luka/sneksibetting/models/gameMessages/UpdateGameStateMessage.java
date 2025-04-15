package com.luka.sneksibetting.models.gameMessages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.luka.sneksibetting.models.snake.Board;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.HashMap;
import java.util.Map;

public class UpdateGameStateMessage extends Message {
    private final String gameId;
    private final Integer[][] player1Board;
    private final Integer[][] player2Board;
    private final Integer player1Score;
    private final Integer player2Score;
    private final String player1Username;
    private final String player2Username;
    private final Integer player1HeadPosI;
    private final Integer player1HeadPosJ;
    private final Integer player2HeadPosI;
    private final Integer player2HeadPosJ;
    private final Integer numWatching;

    public UpdateGameStateMessage(String gameId, Integer numWatching, String player1Username, String player2Username,
                                  Board p1Board, Board p2Board) {
        super(4);
        this.gameId = gameId;
        this.numWatching = numWatching;
        this.player1Board = new Integer[10][];
        this.player2Board = new Integer[10][];
        this.player1Username = player1Username;
        this.player2Username = player2Username;

        for (int i = 0; i < 10; i++) {
            this.player1Board[i] = new Integer[10];
            this.player2Board[i] = new Integer[10];
            for (int j = 0; j < 10; j++) {
                this.player1Board[i][j] = 0;
                this.player2Board[i][j] = 0;
            }
        }

        this.player1HeadPosI = p1Board.Snake.Head.getPosI();
        this.player1HeadPosJ = p1Board.Snake.Head.getPosJ();
        this.player2HeadPosI = p2Board.Snake.Head.getPosI();
        this.player2HeadPosJ = p2Board.Snake.Head.getPosJ();

        for (int i = 0; i < p1Board.Snake.Body.size(); i++) {
            this.player1Board[p1Board.Snake.Body.get(i).getPosI()][p1Board.Snake.Body.get(i).getPosJ()] = 1;
        }
        for (int i = 0; i < p2Board.Snake.Body.size(); i++) {
            this.player1Board[p2Board.Snake.Body.get(i).getPosI()][p2Board.Snake.Body.get(i).getPosJ()] = 1;
        }
        this.player1Board[p1Board.Food.getPosI()][p1Board.Food.getPosJ()] = 2;
        this.player2Board[p2Board.Food.getPosI()][p2Board.Food.getPosJ()] = 2;
        this.player1Score = p1Board.Snake.Body.size() - 1;
        this.player2Score = p2Board.Snake.Body.size() - 1;
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
        msgObj.put("numWatching", this.numWatching);
        msgObj.put("player1Username", this.player1Username);
        msgObj.put("player2Username", this.player2Username);

        ObjectWriter objectWriter = new ObjectMapper().writer();
        String json = objectWriter.writeValueAsString(msgObj);

        dataOutputStream.writeUTF(json);

        return byteArrayOutputStream.toByteArray();
    }
}
