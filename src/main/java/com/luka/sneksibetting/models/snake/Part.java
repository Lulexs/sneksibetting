package com.luka.sneksibetting.models.snake;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Part {
    private Integer dirI;
    private Integer dirJ;
    private Integer posI;
    private Integer posJ;

    public Part(Integer dirI, Integer dirJ, Integer posI, Integer posJ) {
        this.dirI = dirI;
        this.dirJ = dirJ;
        this.posI = posI;
        this.posJ = posJ;
    }

    public Part(String zson) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(zson, new TypeReference<Map<String, Object>>() {});

        this.dirI = (Integer) map.get("dirI");
        this.dirJ = (Integer) map.get("dirJ");
        this.posI = (Integer) map.get("posI");
        this.posJ = (Integer) map.get("posJ");
    }

    public String ToZson() {
        Map<String, Object> objectMap = new HashMap<>();

        objectMap.put("dirI", dirI);
        objectMap.put("dirJ", dirJ);
        objectMap.put("posI", posI);
        objectMap.put("posJ", posJ);

        ObjectWriter objectWriter = new ObjectMapper().writer();
        try {
            return objectWriter.writeValueAsString(objectMap);
        }
        catch (Exception ec) {
            return "";
        }
    }

    public void Move() {
        this.posI += this.dirI;
        this.posJ += this.dirJ;

        if (this.posI == 10) {
            this.posI = 0;
        } else if (this.posI < 0) {
            this.posI = 9;
        } else if (this.posJ == 10) {
            this.posJ = 0;
        } else if (this.posJ < 0) {
            this.posJ = 9;
        }
    }

    public Integer getDirI() {
        return dirI;
    }

    public void setDirI(Integer dirI) {
        this.dirI = dirI;
    }

    public void setDirJ(Integer dirJ) {
        this.dirJ = dirJ;
    }

    public Integer getDirJ() {
        return dirJ;
    }

    public void setPosI(Integer posI) {
        this.posI = posI;
    }

    public Integer getPosI() {
        return posI;
    }

    public void setPosJ(Integer posJ) {
        this.posJ = posJ;
    }

    public Integer getPosJ() {
        return posJ;
    }
}
