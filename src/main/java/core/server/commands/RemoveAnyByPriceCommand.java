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
import core.server.ServerCommandManager;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Class providing removing by price command
 */
public class RemoveAnyByPriceCommand extends AbstractCommand<ServerCommandManager> {

    public RemoveAnyByPriceCommand(ServerCommandManager manager) {
        super(manager);
    }
    @Override
    public void handle(String[] arguments, CommandContextPack context) throws IOException {
        InfoPack pack = new InfoPack();

        StringBuilder builder = new StringBuilder();

        if(arguments.length == 0){
            builder.append("need argument");
        }else{
            try {
                if(manager.getCollection().isEmpty()){
                    builder.append("Collection is empty");
                }else{
                    TicketDAO dao = manager.getTicketDAO();
                    UserDAO userDAOImpl = manager.getUserDAO();
                    List<Ticket> all = dao.all().stream().filter(x -> x.getUser().equals(context.getUser())).map(TicketWrap::getTicket).collect(Collectors.toList());
                    if(all.size() == 0){
                        builder.append("You dont have any elements");
                    }else{
                        all.stream().filter(x -> x.getPrice() == Integer.parseInt(arguments[0])).findFirst().ifPresent((x) -> {
                            manager.getCollection().removeByID(x.getId());
                            dao.deleteByID(x.getId());
                            builder.append("deleted");
                        });
                        builder.append(" done");
                    }

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
        return "Remove element, where price is equals arg";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("remove_any_by_price");
    }

    @Override
    public CommandExternalInfo externalInfo() {
        CommandExternalInfo commandExternalInfo = new CommandExternalInfo(false, true);
        commandExternalInfo.localizedHelp
                .addHelp(Locale.ENGLISH, getHelp())
                .addHelp(new Locale("ru"), "Удаляет любой элемент по заданной цене")
                .addHelp(new Locale("no"), "Fjerner ethvert element til en gitt pris")
                .addHelp(new Locale("hu"), "Eltávolít minden elemet egy adott áron");
        return commandExternalInfo;
    }

    @Override
    public boolean isModifiable() {
        return true;
    }
}
