package core.client.commands;

import core.AbstractCommand;
import core.client.ClientCommandManager;
import core.packet.CommandContextPack;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class LogCommand extends AbstractCommand<ClientCommandManager> {

    public LogCommand(ClientCommandManager manager) {
        super(manager);
    }

    @Override
    public void handle(String[] arguments, CommandContextPack context) throws IOException {
        if(arguments.length != 1){
            System.out.println("log out or log stat to run it");
            return;
        }
        if(arguments[0].equalsIgnoreCase("out")){
            manager.setClient(null);
            System.out.println("logged out");
        }else if(arguments[0].equalsIgnoreCase("stat")){
            if(manager.getUser() == null){
                System.out.println("You are logged out");
            }else{
                System.out.println("You are: " + manager.getUser().getLogin());
            }
        }
    }

    @Override
    public String getHelp() {
        return "Helps logout and check log status";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("log");
    }
}
