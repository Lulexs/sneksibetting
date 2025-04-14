package com.luka.sneksibetting.models.gameMessages;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.Serializable;

public class HelloMessage extends Message implements Serializable {
    private String userId;

    @JsonCreator
    public HelloMessage(@JsonProperty("userId") String userId) {
        super(1);
        this.setUserId(userId);
    }

    @Override
    public byte[] GetBytes() throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);

        dataOutputStream.writeInt(id);

        var msgObj = new Object() {
            final String userId = HelloMessage.this.getUserId();
        };

        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        dataOutputStream.writeUTF(objectWriter.writeValueAsString(msgObj));

        return byteArrayOutputStream.toByteArray();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
