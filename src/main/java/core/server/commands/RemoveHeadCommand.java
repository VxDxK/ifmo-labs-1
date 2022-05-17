package core.server.commands;

import core.AbstractCommand;
import core.pojos.TicketWrap;
import core.server.database.TicketDAO;
import core.server.database.TicketDAOImpl;
import core.server.database.UserDAO;
import core.server.database.UserDAOImpl;
import util.CommandExternalInfo;
import util.SerializationHelper;
import core.packet.CommandContextPack;
import core.packet.InfoPack;
import core.pojos.Ticket;
import core.server.ServerCommandManager;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class providing remove head command
 */
public class RemoveHeadCommand extends AbstractCommand<ServerCommandManager> {

    public RemoveHeadCommand(ServerCommandManager manager) {
        super(manager);
    }

    @Override
    public synchronized void handle(String[] arguments, CommandContextPack context) throws IOException {

        InfoPack pack = new InfoPack();

        StringBuilder builder = new StringBuilder();

        TicketDAO dao = manager.getTicketDAO();
        UserDAO userDAOImpl = manager.getUserDAO();

        List<TicketWrap> all = dao.all().stream().filter(x -> x.getUser().equals(context.getUser())).collect(Collectors.toList());

        if(all.size() == 0){
            builder.append("You dont have any elements");
        }else{
            builder.append(all.get(0));
            dao.delete(all.get(0));
            manager.getCollection().remove(all.get(0));
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

    @Override
    public CommandExternalInfo externalInfo() {
        return new CommandExternalInfo(false, true);
    }

}
