package core;

import core.commands.Command;
import core.commands.CommandContext;
import java.io.*;
import java.nio.file.Path;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public final class CommandManager implements AutoCloseable {
    private final EncapsulatedCollection collection;
    private final BufferedReader reader;
    private final OutputStreamWriter writer;
    private final List<Command> commands = new ArrayList<>();
    private final Map<String, Command> commandAliases = new HashMap<>();
    private final Path file;

    public CommandManager(InputStream in, OutputStream out, EncapsulatedCollection collection, Path file){
        this.reader = new BufferedReader(new InputStreamReader(in));
        this.writer = new OutputStreamWriter(out);
        this.collection = collection;
        this.file = file;
    }

    @Deprecated
    public CommandManager addCommand(BiFunction<BufferedReader, OutputStreamWriter, Command> command){
        return addCommand(command.apply(reader, writer));
    }

    public CommandManager addCommand(Function<CommandManager, Command> command){
        return addCommand(command.apply(this));
    }

    public CommandManager addCommand(Supplier<Command> command){
        return addCommand(command.get());
    }

    private CommandManager addCommand(Command command){
        command.getAliases().forEach(x -> commandAliases.put(x, command));
        commands.add(command);
        return this;
    }

    public void handle(String str) throws IOException{
        handle(str, reader, true);
//        String[] splitted = reader.readLine().trim().split(" ");
//        Command now = commandAliases.get(splitted[0].toLowerCase(Locale.ROOT));
//        if(now != null){
//            now.handle(Arrays.copyOfRange(splitted, 1, splitted.length), new CommandContext(reader, true));
//        }else{
//            writer.write("No such command\n");
//            writer.flush();
//        }
    }

    public void handle(BufferedReader reader) throws IOException {
        handle(reader.readLine(), reader, false);
//        String[] splitted = reader.readLine().trim().split(" ");
//        Command now = commandAliases.get(splitted[0].toLowerCase(Locale.ROOT));
//        if(now != null){
//            now.handle(Arrays.copyOfRange(splitted, 1, splitted.length), new CommandContext(reader, false));
//        }else{
//            writer.write("Stream exec failed\n");
//            writer.flush();
//        }
    }

    public void handle(String command, BufferedReader reader, boolean interactive) throws IOException {
        String[] splitted = command.trim().split(" ");
        Command now = commandAliases.get(splitted[0].toLowerCase(Locale.ROOT));
        if(now != null){
            now.handle(Arrays.copyOfRange(splitted, 1, splitted.length), new CommandContext(reader, interactive));
        }else{
            writer.write("No such command: " + splitted[0].toLowerCase(Locale.ROOT) + '\n');
            writer.flush();
        }
    }

    public BufferedReader getReader() {
        return reader;
    }

    public OutputStreamWriter getWriter() {
        return writer;
    }

    public Map<String, Command> getCommandAliases() {
        return commandAliases;
    }

    public List<Command> getCommands() {
        return commands;
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }

    public Path getFile() {
        return file;
    }

    public EncapsulatedCollection getCollection() {
        return collection;
    }



}
