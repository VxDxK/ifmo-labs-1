package core.client;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class WaitAnswer{

    public boolean waitReceive(DatagramChannel channel, ByteBuffer byteBuffer, long timeout){
        try {
            long start = System.currentTimeMillis();
            while (System.currentTimeMillis() - start < (timeout * 1000)){
                SocketAddress address = channel.receive(byteBuffer);
                if(address != null){
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean waitReceive(DatagramChannel channel, ByteBuffer byteBuffer){
        return waitReceive(channel, byteBuffer, 5);
    }


}
