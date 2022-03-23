package core.commands;

import core.CommandManager;
import core.EncapsulatedCollection;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
/**
 * Class providing show command
 */
public class ShowCommand implements Command{
    private final CommandManager manager;
    public ShowCommand(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(String[] arguments, CommandContext context) {
        EncapsulatedCollection collection = manager.getCollection();
        try {
            manager.getWriter().write("Show: {");
            StringBuilder builder = new StringBuilder();
            collection.forEach(x -> builder.append(x.toString()).append(" "));
            manager.getWriter().write(builder + "}\n");
            manager.getWriter().flush();
        } catch (IOException e) {
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
}
