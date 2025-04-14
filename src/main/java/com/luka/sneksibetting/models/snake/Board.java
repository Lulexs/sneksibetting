package com.luka.sneksibetting.models.snake;

public class Board {
    public Snake Snake;
    public Part Food;

    public Board() {
        Snake = new Snake();
        Food = new Part(0, 0, 3, 3);
    }


}
