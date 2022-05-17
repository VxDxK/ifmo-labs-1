package core.server.commands;

import core.AbstractCommand;
import core.pojos.TicketWrap;
import util.CommandExternalInfo;
import util.SerializationHelper;
import core.packet.CommandContextPack;
import core.packet.InfoPack;
import core.pojos.Ticket;
import core.server.ServerCommandManager;
import core.server.ValidationException;
import util.TicketBuilderComparator;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;
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
    public synchronized void handle(String[] arguments, CommandContextPack context) throws IOException {
        InfoPack pack = new InfoPack();

        StringBuilder builder = new StringBuilder();

        manager.getCollection().stream().map(TicketWrap::getTicket).max(Ticket::compareTo).ifPresent((t) -> {
            Ticket.TicketBuilder ticketBuilder = new Ticket.TicketBuilder(t);
            TicketBuilderComparator comparator = new TicketBuilderComparator();
            if(comparator.compare(ticketBuilder, context.getElement()) > 0){
                try {
                    int newId = manager.getSeq().nextValue();
                    Ticket.TicketBuilder ticketB2 = context.getElement();
                    ticketB2.setId(newId);
                    ticketB2.setCreationDate(LocalDateTime.now());
                    if(manager.getUserDAO().checkAuth(context.getUser())){
                        builder.append("No such user in db");
                    }else{
                        TicketWrap wrap = new TicketWrap(ticketB2.build(), context.getUser());
                        manager.getTicketDAO().add(wrap);
                        manager.getCollection().add(wrap);
                        builder.append("Element added");
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
    public CommandExternalInfo externalInfo() {
        return new CommandExternalInfo(true, true);
    }
}
