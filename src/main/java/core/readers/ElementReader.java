package core.readers;

import java.io.*;

/**
 * Class to read pojo or pojo builder from stream
 * @param <T> type of reading element
 */
public abstract class ElementReader<T> {
    /**
     * Interactive mode
     */
    public abstract T read(BufferedReader reader, OutputStreamWriter writer) throws IOException;

    /**
     * File mode
     */
    public abstract T read(BufferedReader reader) throws IOException;
}
