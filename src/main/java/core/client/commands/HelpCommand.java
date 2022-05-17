package core.client.commands;

import core.AbstractCommand;
import core.client.ClientCommandManager;
import core.packet.CommandContextPack;

import java.util.Arrays;
import java.util.List;
/**
 * Class providing help command
 */
//TODO to server
public class HelpCommand extends AbstractCommand<ClientCommandManager> {

    public HelpCommand(ClientCommandManager manager) {
        super(manager);
    }

    @Override
    public void handle(String[] arguments, CommandContextPack context) {
        System.out.println(manager.getDocs());
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
