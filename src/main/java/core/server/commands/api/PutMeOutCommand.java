package core.server.commands.api;

import core.AbstractCommand;
import core.packet.CommandContextPack;
import core.server.ServerCommandManager;
import util.CommandExternalInfo;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class PutMeOutCommand extends AbstractCommand<ServerCommandManager> {
    public PutMeOutCommand(ServerCommandManager manager) {
        super(manager);
    }

    @Override
    public void handle(String[] arguments, CommandContextPack context) throws IOException {
        manager.getAddressList().remove(context.getSocketAddress());
    }

    @Override
    public String getHelp() {
        return "#api";
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("put_me_out");
    }

    @Override
    public CommandExternalInfo externalInfo() {
        return new CommandExternalInfo(false, false, false);
    }
}
