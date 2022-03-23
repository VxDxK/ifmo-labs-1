package core.commands;

import java.util.Arrays;
import java.util.List;

/**
 * Class that help us stop app
 */
public class ExitCommand implements Command {
    @Override
    public void handle(String[] arguments, CommandContext context) {
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
