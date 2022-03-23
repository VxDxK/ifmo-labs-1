package core.commands;

import core.CommandManager;
import core.pojos.Ticket;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;
/**
 * Class providing remove head command
 */
public class RemoveHeadCommand implements Command{
    private final CommandManager manager;

    public RemoveHeadCommand(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(String[] arguments, CommandContext context) throws IOException {
        Writer writer = manager.getWriter();
        Ticket ticket = manager.getCollection().getSourceCollection().getFirst();
        if(ticket == null){
            writer.write("Collection is empty :( ");
            writer.flush();
            return;
        }
        writer.write(ticket.toString());
        writer.flush();
        manager.getCollection().remove(ticket);
    }

    @Override
    public String getHelp() {
        return "print head of collection and deleting it";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("remove_head");
    }
}
