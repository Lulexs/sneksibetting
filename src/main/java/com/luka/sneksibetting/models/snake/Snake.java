package com.luka.sneksibetting.models.snake;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;

public class Snake {
    public Part Head;
    public ArrayList<Part> Body;
    public HashMap<SimpleEntry<Integer, Integer>, SimpleEntry<Integer, Integer>> Turns;

    public Snake() {
        Head = new Part(1, 0, 5, 5);
        Body = new ArrayList<>();
        Body.add(Head);
        Turns = new HashMap<>();
    }

    public void Move() {
        for (int i = 0; i < Body.size(); i++) {
            SimpleEntry<Integer, Integer> tuple2 = new SimpleEntry<>(Body.get(i).getPosI(), Body.get(i).getPosJ());
            if (Turns.containsKey(tuple2)) {
                SimpleEntry<Integer, Integer> turn = Turns.get(tuple2);
                Body.get(i).setDirI(turn.getKey());
                Body.get(i).setDirJ(turn.getValue());
                if (i == Body.size() - 1) {
                    Turns.remove(tuple2);
                }
            }
            Body.get(i).Move();
        }
    }
}
