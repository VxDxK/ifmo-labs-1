package core.server.commands;

import core.AbstractCommand;
import core.pojos.Ticket;
import core.pojos.TicketWrap;
import core.server.database.TicketDAO;
import core.server.database.TicketDAOImpl;
import core.server.database.UserDAO;
import core.server.database.UserDAOImpl;
import util.CommandExternalInfo;
import util.SerializationHelper;
import core.packet.CommandContextPack;
import core.packet.InfoPack;
import core.server.EncapsulatedCollection;
import core.server.ServerCommandManager;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Class providing show command
 */
public class ShowCommand extends AbstractCommand<ServerCommandManager> {
    public ShowCommand(ServerCommandManager manager) {
        super(manager);
    }

    @Override
    public void handle(String[] arguments, CommandContextPack context) {
        InfoPack pack = new InfoPack();
        StringBuilder builder = new StringBuilder();
        EncapsulatedCollection collection = manager.getCollection();
        builder.append("Show: {");
        synchronized (builder){
            if(arguments != null && arguments.length != 0 && arguments[0].equalsIgnoreCase("my")){
                if(context.getUser() == null){
                    builder.append("Should login to use show my");
                }else{
                    TicketDAO dao = manager.getTicketDAO();
                    UserDAO userDAOImpl = manager.getUserDAO();
                    List<Ticket> all = dao.all().stream().filter(x -> x.getUser()
                            .equals(context.getUser())).map(TicketWrap::getTicket).collect(Collectors.toList());
                    all.forEach(x -> builder.append(x.toString()).append(" "));
                }
            }else{
                collection.forEach(x -> builder.append(x.toString()).append(" "));
            }

            builder.append("}\n");
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
        return "Show all collection elements";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("show");
    }

    @Override
    public CommandExternalInfo externalInfo() {
        CommandExternalInfo commandExternalInfo = super.externalInfo();
        commandExternalInfo.localizedHelp.addHelp(Locale.ENGLISH, getHelp())
                .addHelp(new Locale("ru"), "Строковое представление коллекции")
                .addHelp(new Locale("no"), "String representasjon av samlingen")
                .addHelp(new Locale("hu"), "A gyűjtemény karakterlánc-ábrázolása");
        return commandExternalInfo;
    }
}
