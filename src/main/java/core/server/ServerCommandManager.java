package core.server;

import core.AbstractCommandManager;
import core.Command;
import core.packet.CommandContext;

import java.io.*;
import java.nio.channels.DatagramChannel;
import java.nio.file.Path;
import java.util.function.Function;
import java.util.logging.Logger;

public final class ServerCommandManager extends AbstractCommandManager implements AutoCloseable {

    private final Logger logger = Logger.getLogger(ServerCommandManager.class.getName());

    DatagramChannel channel;
    EncapsulatedCollection collection;
    Path file;

    public ServerCommandManager(EncapsulatedCollection collection, Path file, DatagramChannel socket){
        this.collection = collection;
        this.file = file;
        this.channel = socket;
    }


    public ServerCommandManager addCommand(Function<ServerCommandManager, Command> command){
        addCommand(command.apply(this));
        return this;
    }

    public void handle(String str) throws IOException{
        throw new IOException("Cannot handle just string");
    }

    public void handle(CommandContext context) throws IOException{
        Command now = commandAliases.get(context.getCommandPeek());
        if(now != null){
            if(now.elementRequire()){
                System.out.println(context.getElement().toString());
            }
            now.handle(context.getArgs(), context);
        }
    }

    public DatagramChannel getChannel() {
        return channel;
    }

    public EncapsulatedCollection getCollection() {
        return collection;
    }

    public Path getFile() {
        return file;
    }


    @Override
    public void close(){
        if (channel != null){
            try {
                channel.close();
            }catch (IOException e){
                logger.warning(e.getMessage());
            }
        }
    }
}
