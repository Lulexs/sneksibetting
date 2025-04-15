package com.luka.sneksibetting.models.snake;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;

public class Snake {
    public Part Head;
    public ArrayList<Part> Body;
    public HashMap<SimpleEntry<Integer, Integer>, SimpleEntry<Integer, Integer>> Turns;

    public Snake() {
        Head = new Part(0, 1, 5, 5);
        Body = new ArrayList<>();
        Body.add(Head);
        Turns = new HashMap<>();
    }

    public Snake(String zson) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(zson, new TypeReference<Map<String, Object>>() {});

        this.Head = new Part((String)map.get("Head"));
        List<Map<String, List<Integer>>> serializedTurns = (List<Map<String, List<Integer>>>) map.get("Turns");
        this.Turns = new HashMap<>();
        for (Map<String, List<Integer>> turn : serializedTurns) {
            List<Integer> keyList = turn.get("key");
            List<Integer> valueList = turn.get("value");
            SimpleEntry<Integer, Integer> key = new SimpleEntry<>(keyList.get(0), keyList.get(1));
            SimpleEntry<Integer, Integer> value = new SimpleEntry<>(valueList.get(0), valueList.get(1));
            this.Turns.put(key, value);
        }
        ArrayList<String> stringifiedBody = (ArrayList<String>)map.get("Body");
        this.Body = new ArrayList<>();
        for (String s : stringifiedBody) {
            this.Body.add(new Part(s));
        }
    }

    public void ChangeDir(Character dir) {
        if (dir == 'u' && Head.getDirI() == 0) {
            Turns.put(new SimpleEntry<>(Head.getPosI(), Head.getPosJ()), new SimpleEntry<>(-1, 0));
        }
        else if (dir == 'd' && Head.getDirI() == 0) {
            Turns.put(new SimpleEntry<>(Head.getPosI(), Head.getPosJ()), new SimpleEntry<>(1, 0));
        }
        else if (dir == 'l' && Head.getDirJ() == 0) {
            Turns.put(new SimpleEntry<>(Head.getPosI(), Head.getPosJ()), new SimpleEntry<>(0, -1));
        }
        else if (dir == 'r' && Head.getDirJ() == 0) {
            Turns.put(new SimpleEntry<>(Head.getPosI(), Head.getPosJ()), new SimpleEntry<>(0, 1));
        }
    }

    public String ToZson() throws Exception {
        Map<String, Object> objectMap = new HashMap<>();

        objectMap.put("Head", Head.ToZson());
        ArrayList<String> stringifiedBadi = new ArrayList<>();
        for (Part part: Body) {
            stringifiedBadi.add(part.ToZson());
        }
        objectMap.put("Body", stringifiedBadi);
        ArrayList<Map<String, Object>> serializedTurns = new ArrayList<>();
        for (Map.Entry<SimpleEntry<Integer, Integer>, SimpleEntry<Integer, Integer>> entry : Turns.entrySet()) {
            Map<String, Object> pair = new HashMap<>();
            pair.put("key", Arrays.asList(entry.getKey().getKey(), entry.getKey().getValue()));
            pair.put("value", Arrays.asList(entry.getValue().getKey(), entry.getValue().getValue()));
            serializedTurns.add(pair);
        }
        objectMap.put("Turns", serializedTurns);

        ObjectWriter objectWriter = new ObjectMapper().writer();
        return objectWriter.writeValueAsString(objectMap);
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
