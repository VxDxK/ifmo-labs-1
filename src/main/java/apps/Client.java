package apps;

import core.DeserializationHelper;
import core.SerializationHelper;
import core.client.ClientCommandManager;
import core.client.WaitAnswer;
import core.client.commands.ExecScriptCommand;
import core.client.commands.ExitCommand;
import core.client.commands.HelpCommand;
import core.packet.CommandContext;
import core.packet.StartupPack;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * Client app for 6th lab
 * var 3133
 */
public class Client implements Runnable{


    public Client() {

    }

    @Override
    public void run() {
        SocketAddress address = null;
        try {
            address = getAddress(System.getProperty("servAddress"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }


        ByteBuffer fromServer = ByteBuffer.allocate(2048);

        try(SerializationHelper helper = new SerializationHelper();
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                ClientCommandManager commandManager = new ClientCommandManager(br, address, fromServer)){

            DatagramChannel channel = commandManager.getChannel();
            commandManager
                    .addCommand(HelpCommand::new)
                    .addCommand(ExecScriptCommand::new)
                    .addCommand(ExitCommand::new);

            channel.send(helper.serialize(new CommandContext().setCommandPeek("get_all_commands")), address);
            WaitAnswer waitAnswer = new WaitAnswer();
            System.out.println("Waiting server for 5 seconds");
            if(!waitAnswer.waitReceive(channel, fromServer)){
                System.out.println("Server should work on startup");
                System.exit(-1);
            }

            Object f = DeserializationHelper.get().deSerialization(fromServer);

            if(f instanceof StartupPack){
                StartupPack startupPack = (StartupPack) f;
                commandManager.updateHelp(startupPack.getHelp());
                commandManager.setServerCommands(startupPack.getMap());
            }else{
                System.out.println("Cannot load help. Server is down, try later.");
                System.exit(-1);
            }
            fromServer.clear();
            try{
                while (!Thread.currentThread().isInterrupted()){
                    System.out.print("Enter command: ");
                    commandManager.handle(br.readLine());
                }
            }catch (IOException e){
                System.out.println("Exception on runtime: " + e.getMessage());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private SocketAddress getAddress(String str) throws IOException{
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
