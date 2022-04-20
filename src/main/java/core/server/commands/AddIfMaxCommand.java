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
 * Class providing add operation with if max statement
 */
public class AddIfMaxCommand extends AbstractCommand<ServerCommandManager> {

    public AddIfMaxCommand(ServerCommandManager manager) {
        super(manager);
    }

    @Override
    public void handle(String[] arguments, CommandContext context) throws IOException {
        InfoPack pack = new InfoPack();

        StringBuilder builder = new StringBuilder();

        manager.getCollection().stream().max(Ticket::compareTo).ifPresent((t) -> {
            Ticket.TicketBuilder ticketBuilder = new Ticket.TicketBuilder(t);
            TicketBuilderComparator comparator = new TicketBuilderComparator();
            if(comparator.compare(ticketBuilder, context.getElement()) > 0){
                try {
                    if(manager.getCollection().add(context.getElement())){
                        builder.append("Element added");
                    }else{
                        builder.append("Not added");
                    }
                } catch (ValidationException e) {
                    builder.append(e.getMessage());
                }
            }
        });

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
        return "Add if this element is max";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("add_if_max");
    }

    @Override
    public boolean elementRequire() {
        return true;
    }
}
