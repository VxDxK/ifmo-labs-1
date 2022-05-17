package core.server.database;

public interface Sequence {
    boolean createSchema();

    int nextValue();
}
