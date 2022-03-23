package core.commands;

import core.CommandManager;
import core.pojos.Ticket;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
/**
 * Class providing removing by price command
 */
public class RemoveAnyByPriceCommand implements Command{
    private final CommandManager manager;

    public RemoveAnyByPriceCommand(CommandManager manager) {
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
            int price = Integer.parseInt(arguments[0]);
            for (Ticket ticket: manager.getCollection()) {
                if(ticket.getPrice() == price){
                    manager.getCollection().remove(ticket);
                    return;
                }
            }
        }catch (Exception e){

        }
    }

    @Override
    public String getHelp() {
        return "Remove element, where price is equals arg";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("remove_any_by_price");
    }
}
