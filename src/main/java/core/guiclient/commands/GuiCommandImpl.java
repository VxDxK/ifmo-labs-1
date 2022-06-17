package core.guiclient.commands;

import core.guiclient.GuiCommandManager;
import core.guiclient.commands.GuiCommand;

public abstract class GuiCommandImpl implements GuiCommand {
    protected final GuiCommandManager commandManager;

    public GuiCommandImpl(GuiCommandManager commandManager) {
        this.commandManager = commandManager;
    }
}
