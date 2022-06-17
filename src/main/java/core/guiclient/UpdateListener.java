package core.guiclient;

import core.guiclient.gui.controllers.AppController;
import core.packet.CommandContextPack;
import core.packet.UpdatePack;
import util.DeserializationHelper;
import util.SerializationHelper;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class UpdateListener extends Thread{
    private final AppController controller;
    private final DatagramChannel channel;
    private final SocketAddress serverAddress;
    public UpdateListener(AppController controller, DatagramChannel ch, SocketAddress serverAddress) throws IOException {
        this.controller = controller;
        channel = ch;
        this.serverAddress = serverAddress;
    }

    @Override
    public void run() {
        try(SerializationHelper serializationHelper = new SerializationHelper()) {
            CommandContextPack commandContextPack = new CommandContextPack();
            commandContextPack.setCommandPeek("put_me_in");
            channel.send(serializationHelper.serialize(commandContextPack), serverAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 8);
        while (!isInterrupted()){
            byteBuffer.clear();
            try {
                SocketAddress socketAddress = channel.receive(byteBuffer);
                if(socketAddress != null){
                    Object obj = DeserializationHelper.get().deSerialization(byteBuffer);
                    if(obj instanceof UpdatePack){
                        UpdatePack updatePack = (UpdatePack) obj;
                        controller.updateCollection(updatePack.listOfTickets);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendOutMessage(){
        try(SerializationHelper serializationHelper = new SerializationHelper()) {
            CommandContextPack commandContextPack = new CommandContextPack();
            commandContextPack.setCommandPeek("put_me_out");
            channel.send(serializationHelper.serialize(commandContextPack), serverAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
