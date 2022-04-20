package core.server.commands;

import core.AbstractCommand;
import core.DeserializationHelper;
import core.SerializationHelper;
import core.packet.CommandContext;
import core.packet.InfoPack;
import core.server.ServerCommandManager;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * Class providing info command
 */
public class InfoCommand extends AbstractCommand<ServerCommandManager> {
    private final Logger logger = Logger.getLogger(InfoCommand.class.getName());
    public InfoCommand(ServerCommandManager manager) {
        super(manager);
    }

    @Override
    public void handle(String[] arguments, CommandContext context) {
        System.out.println("SIZE: " + manager.getCollection().size());
        InfoPack pack = new InfoPack();

        String builder = "Collection type: " + manager.getCollection().getType() + "\n" +
                "Init time: " + manager.getCollection().getInitTime().toString() + "\n" +
                "Size: " + manager.getCollection().size() + "\n";

        pack.setString(builder);
        System.out.println(pack.toString());
        try(SerializationHelper serializationHelper = new SerializationHelper()) {
            ByteBuffer byteBuffer = serializationHelper.serialize(pack);
            System.out.println(Arrays.toString(byteBuffer.array()));
            System.out.println(DeserializationHelper.get().deSerialization(byteBuffer));
            manager.getChannel().send(byteBuffer, context.getSocketAddress());
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getHelp() {
        return "Writing info about collection, init time, size, type etc";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("info", "i");
    }
}
