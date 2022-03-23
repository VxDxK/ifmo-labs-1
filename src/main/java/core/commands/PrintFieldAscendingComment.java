package core.commands;

import core.CommandManager;
import core.pojos.Ticket;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Class providing command to print all comments
 */
public class PrintFieldAscendingComment implements Command{
    private final CommandManager manager;

    public PrintFieldAscendingComment(CommandManager manager) {
        this.manager = manager;
    }
    @Override
    public void handle(String[] arguments, CommandContext context) throws IOException {
        manager.getWriter().write(manager.getCollection().stream().map(Ticket::getComment).sorted(String::compareTo)
                .collect(Collectors.toList()) + "\n");
        manager.getWriter().flush();
    }

    @Override
    public String getHelp() {
        return "Print comment field in vozrastanie";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("print_field_ascending_comment");
    }
}
