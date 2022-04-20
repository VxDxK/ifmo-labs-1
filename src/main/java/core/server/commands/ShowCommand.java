package core.server.commands;

import core.AbstractCommand;
import core.SerializationHelper;
import core.packet.CommandContext;
import core.packet.InfoPack;
import core.server.EncapsulatedCollection;
import core.server.ServerCommandManager;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
/**
 * Class providing show command
 */
public class ShowCommand extends AbstractCommand<ServerCommandManager> {
    public ShowCommand(ServerCommandManager manager) {
        super(manager);
    }

    @Override
    public void handle(String[] arguments, CommandContext context) {
        InfoPack pack = new InfoPack();

        StringBuilder builder = new StringBuilder();

        EncapsulatedCollection collection = manager.getCollection();
        builder.append("Show: {");
        collection.forEach(x -> builder.append(x.toString()).append(" "));
        builder.append(builder).append("}\n");
        try(SerializationHelper serializationHelper = new SerializationHelper()) {
            pack.setString(builder.toString());
            ByteBuffer byteBuffer = serializationHelper.serialize(pack);
            manager.getChannel().send(byteBuffer, context.getSocketAddress());
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getHelp() {
        return "Show all collection elements";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("show");
    }
}
