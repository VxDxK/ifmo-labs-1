package core;

import core.packet.CommandContextPack;
import util.CommandExternalInfo;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Class providing base command methods
 */
public interface Command {
    /**
     * Method providing method to exec command with
     * @param arguments command argument
     * @param context provide some stuff to use it in runtime
     */
    void handle(String[] arguments, CommandContextPack context) throws IOException;

    /**
     * @return string with help info
     */
    String getHelp();

    /**
     * @return command name
     */
    default String getName(){
        return getAliases().get(0);
    }

    /**
     * @return all aliases
     */
    List<String> getAliases();

    default CommandExternalInfo externalInfo(){
        CommandExternalInfo commandExternalInfo = new CommandExternalInfo(false, false, true);
        if(getHelp().equals("#api")){
            commandExternalInfo.localizedHelp.addHelp(Locale.ENGLISH, "");
        }else{
            commandExternalInfo.localizedHelp.addHelp(Locale.ENGLISH, getHelp());
        }
        return commandExternalInfo;
    }

    default boolean isModifiable(){
        return false;
    }

}
