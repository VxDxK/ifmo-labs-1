package util;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class AddressValidator {
    public static SocketAddress getAddress(String str) throws IOException {
        if(str == null){
            return new InetSocketAddress("localhost", 8080);
        }
        String[] parsed = str.split(":");
        if(parsed.length != 2){
            throw new IOException("Address format error");
        }

        try {
            Integer.parseInt(parsed[1]);
        }catch (NumberFormatException e){
            throw new IOException("Port should be int");
        }

        if (Integer.parseInt(parsed[1]) < 0 || Integer.parseInt(parsed[1]) > 0xFFFF)
            throw new IOException("port out of range:" + Integer.parseInt(parsed[1]));

        return new InetSocketAddress(parsed[0], Integer.parseInt(parsed[1]));
    }
}
