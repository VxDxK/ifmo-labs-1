package core.client.commands;

import core.client.ClientCommandManager;
import core.AbstractCommand;
import core.packet.CommandContextPack;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
/**
 * Class providing script execution command
 */
public class ExecScriptCommand extends AbstractCommand<ClientCommandManager> {
    /**
     * call stack
     */
    private final Stack<String> callStack;
    public ExecScriptCommand(ClientCommandManager manager) {
        super(manager);
        callStack = new Stack<>();
    }

    @Override
    public void handle(String[] arguments, CommandContextPack context) throws IOException {

        if(arguments.length == 0){
            System.out.println("no file was specified; Call stack: " + Arrays.toString(callStack.toArray()));
        }
        if(callStack.contains(arguments[0])){
            System.out.println("script loop: " + Arrays.toString(callStack.toArray()));
        }



        Path file = Paths.get(arguments[0]);
        if(!Files.exists(file)){
            System.out.println("Script file is not exists " + Arrays.toString(callStack.toArray()));
            return;
        }
        if(!Files.isRegularFile(file)){
            System.out.println("File is not regular file " + Arrays.toString(callStack.toArray()));
            return;
        }
        if(!Files.isReadable(file)){
            System.out.println("File is not readable " + Arrays.toString(callStack.toArray()));
            return;
        }

        try(BufferedReader reader = Files.newBufferedReader(file)){
            callStack.add(arguments[0]);
            while(reader.ready()){
                try {
                    manager.handle(reader);
                }catch (IOException e){
                    System.out.println("Script error: " + e.getMessage() + "\ncallstack: " + Arrays.toString(callStack.toArray()));
                    break;
                }
            }
            callStack.pop();
        }
    }

    @Override
    public String getHelp() {
        return "Execute script in file";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("execute_script", "exec");
    }
}
