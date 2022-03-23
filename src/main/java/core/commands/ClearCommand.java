package core.commands;

import core.CommandManager;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
/**
 * Class providing command to clear in memory collection
 */
public class ClearCommand implements Command{
    private final CommandManager manager;

    public ClearCommand(CommandManager manager) {
        this.manager = manager;
    }
    @Override
    public void handle(String[] arguments, CommandContext context) throws IOException {
        manager.getCollection().clear();
        if(context.isInteractive()){
            manager.getWriter().write("cleared");
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
}
