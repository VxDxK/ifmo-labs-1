package core.server.commands;

import core.AbstractCommand;
import core.pojos.Ticket;
import core.pojos.TicketWrap;
import util.CommandExternalInfo;
import util.SerializationHelper;
import core.packet.CommandContextPack;
import core.packet.InfoPack;
import core.server.ServerCommandManager;
import core.server.ValidationException;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * Class providing add operation
 */
public class AddCommand extends AbstractCommand<ServerCommandManager> {
    private static final Logger logger = Logger.getLogger(AddCommand.class.getName());
    public AddCommand(ServerCommandManager manager) {
        super(manager);
    }

    @Override
    public synchronized void handle(String[] arguments, CommandContextPack context) throws IOException {
        InfoPack pack = new InfoPack();

        StringBuilder builder = new StringBuilder();

        if(context.getElement() == null){
            builder.append("No element was given");
        }else{
            try {
                int newId = manager.getSeq().nextValue();
                Ticket.TicketBuilder ticketBuilder = context.getElement();
                ticketBuilder.setId(newId);
                ticketBuilder.setCreationDate(LocalDateTime.now());

                if(!manager.getUserDAO().checkAuth(context.getUser())){
                    logger.info(context.getUser().toString());
                    builder.append("No such user in db");
                }else{
                    TicketWrap wrap = new TicketWrap(ticketBuilder.build(), context.getUser());
                    manager.getTicketDAO().add(wrap);
                    manager.getCollection().add(wrap);
                    builder.append("Element added");
                }
            } catch (ValidationException e) {
                builder.append("Element is not valid: ").append(e.getMessage());
            }
        }

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
        return "Adding element Ticket to collection";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("add", "a");
    }

    @Override
    public CommandExternalInfo externalInfo() {
        CommandExternalInfo commandExternalInfo = new CommandExternalInfo(true, true);
        commandExternalInfo.localizedHelp
                .addHelp(Locale.ENGLISH, getHelp())
                .addHelp(new Locale("ru"), "Добавляет элемент в коллекцию")
                .addHelp(new Locale("no"), "Legger til et element i samlingen")
                .addHelp(new Locale("hu"), "Hozzáad egy elemet a gyűjteményhez");
        return commandExternalInfo;
    }

    @Override
    public boolean isModifiable() {
        return true;
    }
}
