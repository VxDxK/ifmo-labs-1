package core.commands;

import core.CommandManager;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
/**
 * Class providing remove by id command
 */
public class RemoveById implements Command{
    private final CommandManager manager;

    public RemoveById(CommandManager manager) {
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
            if(manager.getCollection().getById(id) == null){
                if(context.isInteractive()){
                    manager.getWriter().write("No such id");
                    manager.getWriter().flush();
                }
            }
            manager.getCollection().remove(manager.getCollection().getById(id));
        }catch (Exception e){}


    }

    @Override
    public String getHelp() {
        return "Removing element by id";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("remove_by_id");
    }
}
