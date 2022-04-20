package core;

import core.packet.CommandContext;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;

public class DeserializationHelper{

    public Object deSerialization(ByteBuffer inpBuf){
        try(ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(inpBuf.array());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)){
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static DeserializationHelper get(){
        return new DeserializationHelper();
    }

}
