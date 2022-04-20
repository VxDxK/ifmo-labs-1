package core.server.commands;

import core.AbstractCommand;
import core.SerializationHelper;
import core.packet.CommandContext;
import core.packet.InfoPack;
import core.pojos.Ticket;
import core.server.ServerCommandManager;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class providing command to print all comments
 */
public class PrintFieldAscendingComment extends AbstractCommand<ServerCommandManager> {

    public PrintFieldAscendingComment(ServerCommandManager manager) {
        super(manager);
    }
    @Override
    public void handle(String[] arguments, CommandContext context) throws IOException {

        InfoPack pack = new InfoPack();

        StringBuilder builder = new StringBuilder();

        builder.append(manager.getCollection().stream().map(Ticket::getComment).sorted(String::compareTo)
                .collect(Collectors.toList())).append("\n");

        try(SerializationHelper serializationHelper = new SerializationHelper()) {
            pack.setString(builder.toString());
            ByteBuffer byteBuffer = serializationHelper.serialize(pack);
            System.out.println(Arrays.toString(byteBuffer.array()));
            manager.getChannel().send(byteBuffer, context.getSocketAddress());
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getHelp() {
        return "Print comment field in vozrastanie";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("print_field_ascending_comment");
    }
}
