package core.server.commands;

import core.AbstractCommand;
import core.pojos.Ticket;
import core.server.EncapsulatedCollection;
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
/**
 * Class providing command to clear in memory collection
 */
public class ClearCommand extends AbstractCommand<ServerCommandManager> {

    public ClearCommand(ServerCommandManager manager) {
        super(manager);
    }

    @Override
    public synchronized void handle(String[] arguments, CommandContextPack context) throws IOException {
        InfoPack pack = new InfoPack();

        StringBuilder builder = new StringBuilder();
        EncapsulatedCollection collection = manager.getCollection();

        TicketDAO dao = manager.getTicketDAO();
        UserDAO userDAOImpl = manager.getUserDAO();

        collection.stream().filter(x -> x.getUser().equals(context.getUser())).forEach(x -> dao.deleteByID(x.getTicket().getId()));
        collection.stream().filter(x -> x.getUser().equals(context.getUser())).forEach(collection::remove);

        builder.append("Cleared from your elements");

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
        return "Clear collection";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("clear");
    }

    @Override
    public CommandExternalInfo externalInfo() {
        return new CommandExternalInfo(false, true);
    }
}
