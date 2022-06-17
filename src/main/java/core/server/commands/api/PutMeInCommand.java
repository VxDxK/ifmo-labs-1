package core.server.commands.api;

import core.AbstractCommand;
import core.packet.CommandContextPack;
import core.packet.UpdatePack;
import core.server.ServerCommandManager;
import util.CommandExternalInfo;
import util.SerializationHelper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class PutMeInCommand extends AbstractCommand<ServerCommandManager> {
    public PutMeInCommand(ServerCommandManager manager) {
        super(manager);
    }

    @Override
    public void handle(String[] arguments, CommandContextPack context) throws IOException {
        manager.getAddressList().add(context.getSocketAddress());
        try(SerializationHelper helper = new SerializationHelper()){
            UpdatePack updatePack = new UpdatePack();
            updatePack.listOfTickets.addAll(manager.getCollection());
            manager.getChannel().send(helper.serialize(updatePack), context.getSocketAddress());
        }
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
        return Arrays.asList("put_me_in");
    }

    @Override
    public CommandExternalInfo externalInfo() {
        return new CommandExternalInfo(false, false, false);
    }
}
