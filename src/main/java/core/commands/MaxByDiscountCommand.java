package core.commands;

import core.CommandManager;
import core.pojos.Ticket;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;
/**
 * Class providing command with ticket max by discount
 */
public class MaxByDiscountCommand implements Command{
    private final CommandManager manager;

    public MaxByDiscountCommand(CommandManager manager) {
        this.manager = manager;
    }
    @Override
    public void handle(String[] arguments, CommandContext context) throws IOException {
        Ticket ansTicket = null;
        int discount = -1;
        for (Ticket ticket: manager.getCollection()) {
            if(ticket.getDiscount() > discount){
                discount = ticket.getDiscount();
                ansTicket = ticket;
            }
        }
        Writer writer = manager.getWriter();
        if(discount == -1){
            writer.write("Collection is empty");
            writer.flush();
        }else{
            writer.write(ansTicket.toString());
            writer.flush();
        }
    }

    @Override
    public String getHelp() {
        return "Returns max by discount";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("max_by_discount");
    }
}
