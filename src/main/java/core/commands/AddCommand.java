package core.commands;

import core.CommandManager;
import core.ValidationException;
import core.pojos.Ticket;
import core.readers.TicketReader;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;

/**
 * Class providing add operation
 */
public class AddCommand implements Command{
    private final CommandManager manager;

    public AddCommand(CommandManager manager) {
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
                writer.write("Error with input from file\n");
                writer.flush();
                return;
            }
        }

        try {
            manager.getCollection().add(builder);
            if(context.isInteractive()){
                manager.getWriter().write("Element added");
            }
        } catch (ValidationException e) {
            Writer writer = manager.getWriter();
            writer.write("Your Ticket is not valid: " + e.getMessage());
            writer.flush();
        }
    }

    @Override
    public String getHelp() {
        return "Adding element Ticket to collection";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("add", "a");
    }
}
