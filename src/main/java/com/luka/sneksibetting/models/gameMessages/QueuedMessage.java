package com.luka.sneksibetting.models.gameMessages;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class QueuedMessage extends Message {

    public QueuedMessage() {
        super(2);
    }

    @Override
    public byte[] GetBytes() throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);

        dataOutputStream.writeInt(id);

        return byteArrayOutputStream.toByteArray();
    }
}
