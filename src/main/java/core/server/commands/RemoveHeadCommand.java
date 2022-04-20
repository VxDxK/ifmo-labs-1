package core.server.commands;

import core.AbstractCommand;
import core.SerializationHelper;
import core.packet.CommandContext;
import core.packet.InfoPack;
import core.pojos.Ticket;
import core.server.ServerCommandManager;
import core.server.ValidationException;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
/**
 * Class providing remove head command
 */
public class RemoveHeadCommand extends AbstractCommand<ServerCommandManager> {

    public RemoveHeadCommand(ServerCommandManager manager) {
        super(manager);
    }

    @Override
    public void handle(String[] arguments, CommandContext context) throws IOException {

        InfoPack pack = new InfoPack();

        StringBuilder builder = new StringBuilder();


//        Writer writer = manager.getWriter();
        Ticket ticket = manager.getCollection().getSourceCollection().getFirst();
        if(ticket == null){
            builder.append("Collection is empty :( ");
        }else {
            builder.append(ticket);
            manager.getCollection().remove(ticket);
        }

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
        return "print head of collection and deleting it";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("remove_head");
    }
}
