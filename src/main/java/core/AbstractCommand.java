package core;

public abstract class AbstractCommand<T extends AbstractCommandManager> implements Command {
    protected final T manager;

    public AbstractCommand(T manager) {
        this.manager = manager;
    }

}
