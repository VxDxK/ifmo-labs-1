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
import java.util.Locale;

/**
 * Class providing add operation with if min statement
 */
public class AddIfMinCommand extends AbstractCommand<ServerCommandManager> {

    public AddIfMinCommand(ServerCommandManager manager) {
        super(manager);
    }
    @Override
    public void handle(String[] arguments, CommandContextPack context) throws IOException {
        InfoPack pack = new InfoPack();

        StringBuilder builder = new StringBuilder();

        manager.getCollection().stream().map(TicketWrap::getTicket).max(Ticket::compareTo).ifPresent((t) -> {
            Ticket.TicketBuilder ticketBuilder = new Ticket.TicketBuilder(t);
            TicketBuilderComparator comparator = new TicketBuilderComparator();
            if(comparator.compare(ticketBuilder, context.getElement()) < 0){
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
        return "adding if its minimal element";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("add_if_min");
    }

    @Override
    public CommandExternalInfo externalInfo() {
        CommandExternalInfo commandExternalInfo = new CommandExternalInfo(true, true);
        commandExternalInfo.localizedHelp
                .addHelp(Locale.ENGLISH, getHelp())
                .addHelp(new Locale("ru"), "Добавляет элемент в коллекцию если он наименьший")
                .addHelp(new Locale("no"), "Legger til et element i samlingen hvis det er størst")
                .addHelp(new Locale("hu"), "Hozzáad egy elemet a gyűjteményhez, ha az a legnagyobb");
        return commandExternalInfo;
    }

    @Override
    public boolean isModifiable() {
        return true;
    }
}
