package core.client.commands;

import core.AbstractCommand;
import core.client.ClientCommandManager;
import core.packet.CommandContextPack;
import core.pojos.TicketWrap;
import util.CommandExternalInfo;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ClientUpdateCommand extends AbstractCommand<ClientCommandManager> {

    public ClientUpdateCommand(ClientCommandManager manager) {
        super(manager);
    }

    @Override
    public void handle(String[] arguments, CommandContextPack context) throws IOException {
        if(arguments.length == 0){
            System.out.println("Please enter id");
            return;
        }
        try {
            int id = Integer.parseInt(arguments[0]);
            manager.handle("get_elements");
            List<TicketWrap> ticketWraps = manager.getWrapList();
            if(ticketWraps == null || ticketWraps.stream().noneMatch(x -> x.getTicket().getId() == id)){
                System.out.println("No elements with this id");
                return;
            }
            TicketWrap ticketWrap = ticketWraps.stream().filter(x -> x.getTicket().getId() == id).collect(Collectors.toList()).get(0);
            if(!ticketWrap.getUser().equals(manager.getUser())){
                System.out.println("Thats not your element");
                return;
            }
            manager.handle("api_update " + id);
        }catch (NumberFormatException e){
            System.out.println("id is not int");
        }
    }

    @Override
    public String getHelp() {
        return "Update given element";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("update");
    }

    @Override
    public CommandExternalInfo externalInfo() {
        return new CommandExternalInfo(false, true);
    }
}
