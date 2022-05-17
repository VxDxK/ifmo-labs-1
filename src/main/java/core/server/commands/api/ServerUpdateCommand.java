package core.server.commands.api;

import core.AbstractCommand;
import core.pojos.Ticket;
import core.pojos.TicketWrap;
import core.server.database.TicketDAO;
import core.server.database.UserDAO;
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
/**
 * Class providing update command
 */
public class ServerUpdateCommand extends AbstractCommand<ServerCommandManager> {

    public ServerUpdateCommand(ServerCommandManager manager) {
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
                if(manager.getCollection().getById(Integer.parseInt(arguments[0])) == null){
                    builder.append("no such element");
                }else{
                    int id = Integer.parseInt(arguments[0]);
                    TicketDAO dao = manager.getTicketDAO();
                    UserDAO userDAOImpl = manager.getUserDAO();
                    List<TicketWrap> all = dao.all();

                    all.stream().filter(x -> x.getUser()
                            .equals(context.getUser()))
                            .map(TicketWrap::getTicket)
                            .filter(x -> x.getId() == id).findFirst().ifPresent(x ->{
                        System.out.println(x);
                        manager.getCollection().removeByID(x.getId());
                        dao.deleteByID(x.getId());

                        int newId = manager.getSeq().nextValue();

                        Ticket.TicketBuilder ticketBuilder = context.getElement();
                        ticketBuilder.setCreationDate(LocalDateTime.now());
                        ticketBuilder.setId(newId);

                        try {
                            TicketWrap wrap = new TicketWrap(ticketBuilder.build(), context.getUser());

                            dao.add(wrap);
                            manager.getCollection().add(wrap);
                            builder.append("update");
                        } catch (ValidationException e) {
                            builder.append("ValidEx: ").append(e.getMessage());
                        }

                    });

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
        return "#api";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("api_update");
    }

    @Override
    public CommandExternalInfo externalInfo() {
        return new CommandExternalInfo(true, true);
    }
}
