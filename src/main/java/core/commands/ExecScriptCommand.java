package core.commands;

import core.CommandManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
/**
 * Class providing script execution command
 */
public class ExecScriptCommand implements Command{
    private final CommandManager manager;
    /**
     * call stack
     */
    private final Stack<String> callStack;
    {
        callStack = new Stack<>();
    }
    public ExecScriptCommand(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(String[] arguments, CommandContext context) throws IOException {
        Writer writer = manager.getWriter();
        if(arguments.length == 0){
            if(context.isInteractive()){
                writer.write("no file was specified\n");
                writer.flush();
                return;
            }else{
                throw new IOException("Error parsing script file; no script file in exec command\n");
            }
        }
        if(callStack.contains(arguments[0])){
            throw new IOException("Call script loop found");
        }
        Path file = Paths.get(arguments[0]);
        if(!Files.exists(file)){
            if(context.isInteractive()){
                writer.write("File is not exists");
                writer.flush();
                return;
            }else{
                throw new IOException("Error parsing script file; File is not exists\n");
            }
        }
        if(!Files.isRegularFile(file)){
            if(context.isInteractive()){
                writer.write("File is not regular file\n");
                writer.flush();
                return;
            }else{
                throw new IOException("Error parsing script file; File is not regular file\n");
            }
        }
        if(!Files.isReadable(file)){
            if(context.isInteractive()){
                writer.write("File is not readable");
                writer.flush();
                return;
            }else{
                throw new IOException("Error parsing script file; File is not readable\n");
            }
        }

        try(BufferedReader reader = Files.newBufferedReader(file)){
            callStack.add(arguments[0]);
            while(reader.ready()){
                manager.handle(reader);
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
