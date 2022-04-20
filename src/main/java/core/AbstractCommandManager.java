package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractCommandManager {
    protected final List<Command> commands = new ArrayList<>();
    protected final Map<String, Command> commandAliases = new HashMap<>();



    protected AbstractCommandManager addCommand(Command command){
        command.getAliases().forEach(x -> commandAliases.put(x, command));
        commands.add(command);
        return this;
    }

    public abstract void handle(String str) throws Exception;

    public Map<String, Command> getCommandAliases() {
        return commandAliases;
    }

    public List<Command> getCommands() {
        return commands;
    }

}
