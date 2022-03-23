package core.commands;

import core.CommandManager;

import java.io.*;
import java.util.Arrays;
import java.util.List;
/**
 * Class providing help command
 */
public class HelpCommand implements Command{
    private final CommandManager manager;
    private String doc;

    public HelpCommand(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(String[] arguments, CommandContext context) {
        if(doc == null){
            StringBuilder docBuilder = new StringBuilder();
            manager.getCommands().forEach(y -> docBuilder.append(y.getName().concat(" : ")).append(y.getHelp().concat("\n")));
            doc = docBuilder.toString();
        }
        try {
            manager.getWriter().write(doc);
            manager.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getHelp() {
        return "Printing help about all commands";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("help", "h");
    }
}
