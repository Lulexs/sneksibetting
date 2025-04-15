package com.luka.sneksibetting.models.snake;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.util.HashMap;
import java.util.Map;

public class Board {
    public Snake Snake;
    public Part Food;

    public Board() {
        Snake = new Snake();
        Food = new Part(0, 0, 3, 3);
    }

    public void Update() {
        Snake.Move();
    }

    public Board(String zson) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(zson, new TypeReference<Map<String, Object>>() {});

        this.Snake = new Snake((String) map.get("Snake"));
        this.Food = new Part((String) map.get("Food"));
    }

    public String ToZson() throws Exception {
        Map<String, Object> obj = new HashMap<>();

        obj.put("Snake", Snake.ToZson());
        obj.put("Food", Food.ToZson());

        ObjectWriter objectWriter = new ObjectMapper().writer();
        return objectWriter.writeValueAsString(obj);
    }


}
