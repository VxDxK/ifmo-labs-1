import apps.Client;
import apps.Server;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Main class with main method
 */

public class Main {
    /**
     *Run app from properties
     */
    public static void main(String[] args){

        String runningMode = System.getProperty("mode");
        if(runningMode == null){
            System.out.println("No such application, specify running mode");
        }else{
            if(runningMode.equalsIgnoreCase("client")){
                Thread serv = new Thread(new Client());
                serv.setName("Server thread");
                serv.start();
            }else if(runningMode.equalsIgnoreCase("server")){
                Thread client = new Thread(new Server());
                client.setName("Client thread");
                client.start();
            }
        }
    }
}
