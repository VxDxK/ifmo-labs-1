package core.client.commands;

import core.AbstractCommand;
import core.client.ClientCommandManager;
import core.packet.CommandContext;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ExitCommand extends AbstractCommand<ClientCommandManager> {

    public ExitCommand(ClientCommandManager manager) {
        super(manager);
    }

    @Override
    public void handle(String[] arguments, CommandContext context) throws IOException {
        Thread.currentThread().interrupt();
    }

    @Override
    public String getHelp() {
        return "Exit, without saving to file";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("exit", "quit", "q", "e");
    }
}
