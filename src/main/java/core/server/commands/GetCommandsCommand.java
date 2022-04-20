package core.server.commands;

import core.AbstractCommand;
import core.SerializationHelper;
import core.packet.CommandContext;
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
    public void handle(String[] arguments, CommandContext context) throws IOException {
        StartupPack startupPack = new StartupPack();
        manager.getCommandAliases().forEach((key, value) -> startupPack.getMap().put(key, value.elementRequire()));
        StringBuilder builder = new StringBuilder();
        manager.getCommands().forEach(y -> builder.append(y.getName().concat(" : ")).append(y.getHelp().concat("\n")));
        startupPack.setHelp(builder.toString());
        try(SerializationHelper serializationHelper = new SerializationHelper()){
            ByteBuffer byteBuffer = serializationHelper.serialize(startupPack);
            manager.getChannel().send(byteBuffer, context.getSocketAddress());
        }
    }

    @Override
    public String getHelp() {
        return "API command";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("get_all_commands");
    }
}
