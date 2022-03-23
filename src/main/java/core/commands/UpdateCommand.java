package core.commands;

import core.CommandManager;
import core.pojos.Ticket;
import core.readers.TicketReader;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;
/**
 * Class providing update command
 */
public class UpdateCommand implements Command{
    private final CommandManager manager;

    public UpdateCommand(CommandManager manager) {
        this.manager = manager;
    }
    @Override
    public void handle(String[] arguments, CommandContext context) throws IOException {
        if(arguments.length == 0){
            if(context.isInteractive()){
                manager.getWriter().write("Something wrong with argument");
                manager.getWriter().flush();
                return;
            }else{
                throw new IOException("Script file error");
            }
        }

        try {
            int id = Integer.parseInt(arguments[0]);
            if(!manager.getCollection().getIdSet().contains(id)){
                if(context.isInteractive()){
                    manager.getWriter().write("no such id");
                    manager.getWriter().flush();
                }else{
                    throw new IOException("Script file error");
                }
            }
        }catch (Exception e){}

        TicketReader reader = new TicketReader();
        Ticket.TicketBuilder builder = new Ticket.TicketBuilder();
        if(context.isInteractive()){
            try {
                builder = reader.read(manager.getReader(), manager.getWriter());
            } catch (IOException e) {
                Writer writer = manager.getWriter();
                writer.write("Error with System IO work: " + e.getMessage());
                writer.flush();
                return;
            }
        }else{
            try {
                builder = reader.read(context.getReader());
            }catch (IOException e){
                Writer writer = manager.getWriter();
                writer.write("Error with System IO work: " + e.getMessage());
                writer.flush();
                return;
            }
        }
        try {
            int id = Integer.parseInt(arguments[0]);
            manager.getCollection().update(id, builder);
        }catch (Exception e){}

    }

    @Override
    public String getHelp() {
        return "Update element by specifier";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("update", "u");
    }
}
