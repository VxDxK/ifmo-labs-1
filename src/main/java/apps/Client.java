package apps;

import core.client.commands.*;
import util.AddressValidator;
import util.SerializationHelper;
import core.client.ClientCommandManager;

import java.io.*;
import java.net.SocketAddress;
import java.nio.ByteBuffer;

/**
 * Client app for 7th lab
 * var 3142
 */
public class Client implements Runnable{


    public Client() {

    }

    @Override
    public void run() {
        SocketAddress address = null;
        try {
            address = AddressValidator.getAddress(System.getProperty("servAddress"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }


        ByteBuffer fromServer = ByteBuffer.allocate(2048);

        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                ClientCommandManager commandManager = new ClientCommandManager(br, address, fromServer)){

            commandManager.handle("get_all_commands");

            commandManager
                    .addCommand(HelpCommand::new)
                    .addCommand(ExecScriptCommand::new)
                    .addCommand(ExitCommand::new)
                    .addCommand(LogCommand::new)
                    .addCommand(ClientUpdateCommand::new);
            try{
                while (!Thread.currentThread().isInterrupted()){
                    System.out.print("Enter command: ");
                    commandManager.handle(br.readLine());
                }
            }catch (IOException e){
                System.out.println("Exception on runtime: " + e.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Some error: " + e.getMessage());
            System.out.println("Stopping this app");
        }
    }
}
