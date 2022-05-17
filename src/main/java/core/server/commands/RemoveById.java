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
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Class providing remove by id command
 */
public class RemoveById extends AbstractCommand<ServerCommandManager> {

    public RemoveById(ServerCommandManager manager) {
        super(manager);
    }
    @Override
    public synchronized void handle(String[] arguments, CommandContextPack context) throws IOException {
        InfoPack pack = new InfoPack();

        StringBuilder builder = new StringBuilder();

        if(arguments.length == 0){
            builder.append("need argument");
        }else{
            try {

                TicketDAO ticketDao = manager.getTicketDAO();
                UserDAO userDAOImpl = manager.getUserDAO();
                List<Ticket> all = ticketDao.all().stream().filter(x -> x.getUser().equals(context.getUser())).map(TicketWrap::getTicket).collect(Collectors.toList());

                if(all.size() == 0){
                    builder.append("You dont have any elements");
                }else{
                    ticketDao.deleteByID(Integer.parseInt(arguments[0]));
                    if(manager.getCollection().getById(Integer.parseInt(arguments[0])) != null){
                        manager.getCollection().remove(Objects.requireNonNull(manager.getCollection().getById(Integer.parseInt(arguments[0]))));
                        builder.append("Ok");
                    }else{
                        builder.append("no such id");
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
        return "Removing element by id";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("remove_by_id");
    }

    @Override
    public CommandExternalInfo externalInfo() {
        return new CommandExternalInfo(false, true);
    }

}
