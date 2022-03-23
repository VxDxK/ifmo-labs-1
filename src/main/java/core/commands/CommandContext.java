package core.commands;

import java.io.BufferedReader;
/**
 * Class providing info for your command execution
 */
public class CommandContext {
    /**
     * Reader of stream, what we handle
     */
    BufferedReader reader;
    /**
     * Flag is interactive
     */
    boolean interactive;

    public CommandContext(BufferedReader reader, boolean interactive) {
        this.reader = reader;
        this.interactive = interactive;
    }


    public BufferedReader getReader() {
        return reader;
    }

    public boolean isInteractive() {
        return interactive;
    }
}
