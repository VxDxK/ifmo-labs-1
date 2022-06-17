package core.server.commands;

import core.AbstractCommand;
import core.pojos.TicketWrap;
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
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Class providing command to print all comments
 */
public class PrintFieldAscendingComment extends AbstractCommand<ServerCommandManager> {

    public PrintFieldAscendingComment(ServerCommandManager manager) {
        super(manager);
    }
    @Override
    public void handle(String[] arguments, CommandContextPack context) throws IOException {

        InfoPack pack = new InfoPack();

        StringBuilder builder = new StringBuilder();

        builder.append(manager.getCollection().stream().map(TicketWrap::getTicket).map(Ticket::getComment).sorted(String::compareTo)
                .collect(Collectors.toList())).append("\n");

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
        return "Print comment field increasing";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("print_field_ascending_comment");
    }

    @Override
    public CommandExternalInfo externalInfo() {
        CommandExternalInfo commandExternalInfo = super.externalInfo();
        commandExternalInfo.localizedHelp.addHelp(Locale.ENGLISH, getHelp())
                .addHelp(new Locale("ru"), "Выводит комментарии по возрастанию")
                .addHelp(new Locale("no"), "Vis kommentarer i stigende rekkefølge")
                .addHelp(new Locale("hu"), "Megjegyzések megjelenítése növekvő sorrendben");
        return commandExternalInfo;
    }

}
