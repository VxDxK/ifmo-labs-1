package core.server.commands.api;

import core.AbstractCommand;
import util.SerializationHelper;
import core.packet.CommandContextPack;
import core.packet.StartupPack;
import core.server.ServerCommandManager;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;

public class GetCommandsCommand extends AbstractCommand<ServerCommandManager> {

    public GetCommandsCommand(ServerCommandManager manager) {
        super(manager);
    }

    @Override
    public void handle(String[] arguments, CommandContextPack context) throws IOException {
        StartupPack startupPack = new StartupPack();
        manager.getCommandAliases().forEach((key, value) -> startupPack.getMap().put(key, value.externalInfo()));
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
        return Arrays.asList("get_all_commands");
    }
}
