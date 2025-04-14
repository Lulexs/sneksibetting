package com.luka.sneksibetting.models.snake;

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

    public void Move() {
        this.posI += this.dirI;
        this.posJ += this.dirJ;
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
