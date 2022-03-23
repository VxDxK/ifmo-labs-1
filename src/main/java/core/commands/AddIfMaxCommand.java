package core.commands;

import core.CommandManager;
import core.ValidationException;
import core.pojos.Ticket;
import core.readers.TicketReader;
import util.TicketBuilderComparator;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;
/**
 * Class providing add operation with if max statement
 */
public class AddIfMaxCommand implements Command{
    private final CommandManager manager;

    public AddIfMaxCommand(CommandManager manager) {
        this.manager = manager;
    }
    @Override
    public void handle(String[] arguments, CommandContext context) throws IOException {
        TicketReader reader = new TicketReader();
        Ticket.TicketBuilder builder = new Ticket.TicketBuilder();
        if(context.isInteractive()){
            try {
                builder = reader.read(manager.getReader(), manager.getWriter());
            } catch (IOException e) {
                Writer writer = manager.getWriter();
                writer.write("Error with System IO work: " + e.getMessage());
                writer.flush();
            }
        }else{
            try {
                builder = reader.read(context.getReader());
            }catch (IOException e){
                Writer writer = manager.getWriter();
                writer.write("Error with System IO work: " + e.getMessage());
                writer.flush();
            }
        }

        try {
            TicketBuilderComparator comparator = new TicketBuilderComparator();
            int ans = comparator.compare(new Ticket.TicketBuilder(manager.getCollection().getMax()), builder);
            if(ans < 0){
                manager.getCollection().add(builder);
                if(context.isInteractive()){
                    manager.getWriter().write("Element added");
                }
            }
        } catch (ValidationException e) {
            Writer writer = manager.getWriter();
            writer.write("Your Ticket is not valid: " + e.getMessage());
            writer.flush();
        }
    }

    @Override
    public String getHelp() {
        return "Add if this element is max";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("add_if_max");
    }
}
