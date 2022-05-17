package core.server.commands.api;

import core.AbstractCommand;
import core.packet.CommandContextPack;
import core.packet.UpdatePack;
import core.server.ServerCommandManager;
import util.CommandExternalInfo;
import util.SerializationHelper;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetElements extends AbstractCommand<ServerCommandManager> {

    public GetElements(ServerCommandManager manager) {
        super(manager);
    }

    @Override
    public void handle(String[] arguments, CommandContextPack context) throws IOException {
        UpdatePack pack = new UpdatePack();
        pack.setString("");

        pack.listOfTickets = new ArrayList<>(manager.getCollection());

        try(SerializationHelper serializationHelper = new SerializationHelper()) {
            ByteBuffer byteBuffer = serializationHelper.serialize(pack);
            manager.getChannel().send(byteBuffer, context.getSocketAddress());
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getHelp() {
        return "#api";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("get_elements");
    }

    @Override
    public CommandExternalInfo externalInfo() {
        return new CommandExternalInfo(false, false);
    }
}
