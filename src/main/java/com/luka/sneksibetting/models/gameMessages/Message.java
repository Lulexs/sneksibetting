package com.luka.sneksibetting.models.gameMessages;

public abstract class Message {
    protected final Integer id;

    public Message(Integer id) {
        this.id = id;
    }

    public abstract byte[] GetBytes() throws Exception;
}
