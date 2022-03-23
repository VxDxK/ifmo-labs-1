package core.commands;

import core.CommandManager;
import core.EncapsulatedCollection;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.List;
/**
 * Class providing info command
 */
public class InfoCommand implements Command{
    private final CommandManager manager;

    public InfoCommand(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(String[] arguments, CommandContext context) {
        OutputStreamWriter writer = manager.getWriter();
        EncapsulatedCollection collection = manager.getCollection();
        try {
            writer.write("Collection type: " + collection.getType() + "\n");
            writer.write("Init time: " + collection.getInitTime().toString() + "\n");
            writer.write("Size: " + collection.size() + "\n");
            writer.flush();
        } catch (IOException e) {
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
}
