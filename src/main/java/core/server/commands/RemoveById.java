package core.server.commands;

import core.AbstractCommand;
import core.SerializationHelper;
import core.packet.CommandContext;
import core.packet.InfoPack;
import core.pojos.Ticket;
import core.server.ServerCommandManager;
import core.server.ValidationException;
import util.TicketBuilderComparator;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
/**
 * Class providing remove by id command
 */
public class RemoveById extends AbstractCommand<ServerCommandManager> {

    public RemoveById(ServerCommandManager manager) {
        super(manager);
    }
    @Override
    public void handle(String[] arguments, CommandContext context) throws IOException {
        InfoPack pack = new InfoPack();

        StringBuilder builder = new StringBuilder();

        if(arguments.length == 0){
            builder.append("need argument");
        }else{
            try {
                if(manager.getCollection().getById(Integer.parseInt(arguments[0])) == null){
                    builder.append("no such element");
                }else{
                    manager.getCollection().remove(manager.getCollection().getById(Integer.parseInt(arguments[0])));
                    builder.append("deleted");
                }

            }catch (NumberFormatException e){
                builder.append("Wrong argument");
            }
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
        return "Removing element by id";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("remove_by_id");
    }
}
