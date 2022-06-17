package core.server.commands.api;

import core.AbstractCommand;
import core.packet.CommandContextPack;
import core.packet.StartupPack;
import core.server.ServerCommandManager;
import util.AddressValidator;
import util.CommandExternalInfo;
import util.SerializationHelper;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class GetUniqueCommandsCommand extends AbstractCommand<ServerCommandManager> {

    Logger logger = Logger.getLogger(GetUniqueCommandsCommand.class.getName());

    public GetUniqueCommandsCommand(ServerCommandManager manager) {
        super(manager);
    }

    @Override
    public void handle(String[] arguments, CommandContextPack context) throws IOException {
        logger.info("in command for unique commands");
        if (arguments.length != 0){
            System.out.println(Arrays.toString(arguments));
            manager.getAddressList().add(context.getSocketAddress());
        }
        StartupPack startupPack = new StartupPack();
        manager.getCommands().forEach(command -> {
            startupPack.getMap().put(command.getName(), command.externalInfo());
        });

        StringBuilder builder = new StringBuilder();
        manager.getCommands().stream()
                .filter(x -> !x.getHelp().equals("#api"))
                .forEach(y -> builder.append(y.getName().concat(" : ")).append(y.getHelp().concat("\n")));
        startupPack.setString(builder.toString());
        try(SerializationHelper serializationHelper = new SerializationHelper()){
            ByteBuffer byteBuffer = serializationHelper.serialize(startupPack);
            manager.getChannel().send(byteBuffer, context.getSocketAddress());
        }
    }

    @Override
    public String getHelp() {
        return "#api";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("get_unique_commands");
    }

    @Override
    public CommandExternalInfo externalInfo() {
        return new CommandExternalInfo(false, false, false);
    }
}
