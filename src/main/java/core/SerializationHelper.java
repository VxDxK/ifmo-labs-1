package core;


import java.io.*;
import java.nio.ByteBuffer;

public class SerializationHelper implements AutoCloseable{
    //Serialization
    ByteArrayOutputStream outputStream;
    ObjectOutputStream oos;



    public SerializationHelper() {
        try {
            outputStream = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(outputStream);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ByteBuffer serialize(Serializable obj){
        try{
            oos.writeObject(obj);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ByteBuffer.wrap(outputStream.toByteArray());
    }



    @Override
    public void close(){
        try {
            outputStream.close();
            oos.close();
        } catch (IOException ignored) {}
    }
}
