package core.server.commands;

import core.AbstractCommand;
import util.CommandExternalInfo;
import util.DeserializationHelper;
import util.SerializationHelper;
import core.packet.CommandContextPack;
import core.packet.InfoPack;
import core.server.ServerCommandManager;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * Class providing info command
 */
public class InfoCommand extends AbstractCommand<ServerCommandManager> {
    private final Logger logger = Logger.getLogger(InfoCommand.class.getName());
    public InfoCommand(ServerCommandManager manager) {
        super(manager);
    }

    @Override
    public synchronized void handle(String[] arguments, CommandContextPack context) {
        System.out.println("SIZE: " + manager.getCollection().size());
        InfoPack pack = new InfoPack();

        String builder = "Collection type: " + manager.getCollection().getType() + "\n" +
                "Init time: " + manager.getCollection().getInitTime().toString() + "\n" +
                "Size: " + manager.getCollection().size() + "\n" +
                "Number of client: " + manager.getAddressList().size() + '\n';

        pack.setString(builder);
        try(SerializationHelper serializationHelper = new SerializationHelper()) {
            ByteBuffer byteBuffer = serializationHelper.serialize(pack);
            manager.getChannel().send(byteBuffer, context.getSocketAddress());
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getHelp() {
        return "Writing info about collection, init time, size, type etc";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("info", "i");
    }

    @Override
    public CommandExternalInfo externalInfo() {
        CommandExternalInfo commandExternalInfo = super.externalInfo();
        commandExternalInfo.localizedHelp.addHelp(Locale.ENGLISH, getHelp())
                .addHelp(new Locale("ru"), "Выводит информацию о коллекции")
                .addHelp(new Locale("no"), "Viser informasjon om samlingen")
                .addHelp(new Locale("hu"), "Információkat jelenít meg a gyűjteményről");
        return commandExternalInfo;
    }
}
