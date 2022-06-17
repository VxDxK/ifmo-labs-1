package util;

import core.LocalizedHelp;

import java.io.Serializable;

public class CommandExternalInfo implements Serializable {
    public boolean elementRequire;
    public boolean needAuth;
    public boolean neededInGui = true;

    public final LocalizedHelp localizedHelp = new LocalizedHelp();

    public CommandExternalInfo(boolean elementRequire, boolean needAuth) {
        this.elementRequire = elementRequire;
        this.needAuth = needAuth;
    }

    public CommandExternalInfo(boolean elementRequire, boolean needAuth, boolean neededInGui) {
        this.elementRequire = elementRequire;
        this.needAuth = needAuth;
        this.neededInGui = neededInGui;
    }

    public CommandExternalInfo setElementRequire(boolean elementRequire) {
        this.elementRequire = elementRequire;
        return this;
    }

    public CommandExternalInfo setNeedAuth(boolean needAuth) {
        this.needAuth = needAuth;
        return this;
    }

    public CommandExternalInfo setNeededInGui(boolean neededInGui) {
        this.neededInGui = neededInGui;
        return this;
    }
}
